package com.zhh.controller.admin;

import com.zhh.configuration.AdminUserSessionMap;
import com.zhh.dto.BaseResp;
import com.zhh.dto.admin.BannerQueryReq;
import com.zhh.repository.BannerRepository;
import com.zhh.repository.BannerRepository;
import com.zhh.repository.entity.AdminUser;
import com.zhh.repository.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController("AdminBannerController")
public class BannerController extends BaseAdminCtl {

    @Autowired
    private BannerRepository bannerRepository;

    @PostMapping("/banner/list")
    public Object bannerList(@RequestBody BannerQueryReq bannerQueryReq) {
        Page bannerList = bannerRepository.findByStatusAndTitleContaining(
                bannerQueryReq.getStatus(),
                bannerQueryReq.getTitle(),
                PageRequest.of(bannerQueryReq.getPage()-1,bannerQueryReq.getLimit()));
        return BaseResp.SUCCESSRESP.setData(bannerList);
    }

    @PostMapping("/banner/create")
    public Object create(@RequestBody Banner banner, HttpServletRequest request) {
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        banner.setAddBy(adminUser.getUserName());
        banner.setAddTime(LocalDateTime.now());
        Banner banner_ = bannerRepository.save(banner);
        return BaseResp.SUCCESSRESP;
    }

    @PostMapping("/banner/edit")
    public Object edit(@RequestBody Banner banner, HttpServletRequest request) {
        Banner banner_ = bannerRepository.findById(banner.getId()).get();
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        banner.setAddBy(banner_.getAddBy());
        banner.setAddTime(banner_.getAddTime());
        banner.setUpBy(adminUser.getUserName());
        banner.setUpTime(LocalDateTime.now());
        bannerRepository.save(banner);
        return BaseResp.SUCCESSRESP;
    }

    @GetMapping("/banner/modifyStatus")
    public Object modifyStatus(@RequestParam Long id,@RequestParam Integer status,
                               HttpServletRequest request) {
        AdminUser adminUser = AdminUserSessionMap.getAdminUserSession(request).getAdminUser();
        int i = bannerRepository.updateStatusById(status.equals(0)?status:1,id,adminUser.getUserName(), LocalDateTime.now());
        if(i>0){
            return BaseResp.SUCCESSRESP;
        }
        return BaseResp.ERRORRESP;
    }

}
