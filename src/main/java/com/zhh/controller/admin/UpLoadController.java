package com.zhh.controller.admin;

import com.zhh.dto.BaseResp;
import com.zhh.utils.FileUtil;
import com.zhh.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;

@Controller
public class UpLoadController extends BaseAdminCtl{

    private static String global_shop_logo_dir = "";

    static {
        String OS = System.getProperty("os.name").toLowerCase();
        global_shop_logo_dir = "/data/temp/img/";
        if (OS.indexOf("windows") >= 0) {
            global_shop_logo_dir = "D:\\data\\temp\\img\\";
        }
    }

    // 图片文件格式
    private static final String[] IMAGE_FILES = {".gif", ".png", ".jpg",
            ".jpeg", ".bmp", "x-icon"};

    // 文件大小限制，单位KB
    private static final long MAX_SIZE = 10000;

    @ResponseBody
    @RequestMapping(value = "/uploadImg.ajax", method = RequestMethod.POST)
    public Object handleImgFileUpload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            // 校验文件大小
            if (!checkFileSize(multipartFile.getSize())) {
                return BaseResp.ERRORRESP;
            }

            // 校验文件类型
            if (!checkImageType(multipartFile.getOriginalFilename())) {
                return BaseResp.ERRORRESP;
            }

            fileName = UUIDUtil.genUUID() + fileName.substring(fileName.lastIndexOf("."));
            byte[] bytes = multipartFile.getBytes();
            FileUtil.writeBytesToFile(bytes,global_shop_logo_dir,fileName);
            return BaseResp.SUCCESSRESP.setData(fileName);
        } catch (Exception e) {
            return BaseResp.ERRORRESP;
        }
    }

    @GetMapping(value = "/img/{file}")
    public void showImg(@PathVariable("file") String fileName,HttpServletResponse response) {
        try {

            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if (StringUtils.equals(".gif", suffixName)) {
                response.setContentType("image/gif");
            } else if (StringUtils.equals(".jpeg", suffixName) || StringUtils.equals(".jpg", suffixName)) {
                response.setContentType("image/jpeg");
            } else if (StringUtils.equals(".png", suffixName)) {
                response.setContentType("image/png");
            } else if (StringUtils.equals(".bmp", suffixName)) {
                response.setContentType("image/bitmap");
            } else if (StringUtils.equals(".ico", suffixName)) {
                response.setContentType("image/x-icon");
            } else {
                response.setContentType("image/gif");
            }

            fileName = global_shop_logo_dir + File.separatorChar + fileName;
            OutputStream out = response.getOutputStream();
            byte[] bytes = FileUtil.readBytesFromFile(fileName);
            if (bytes != null) {
                out.write(bytes);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkImageType(String fileName) {
        Iterator<String> type = Arrays.asList(IMAGE_FILES).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFileSize(long size) {
        if (size > 0L && size < MAX_SIZE * 1024L) {
            return true;
        }
        return false;
    }
}
