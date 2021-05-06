package top.miyamoto.controller;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.miyamoto.service.BaiduService;
import top.miyamoto.service.QuestionBankService;
import top.miyamoto.service.impl.CommonService;

import java.util.Map;

/**
 * @description: 上传文件接口
 * @author: ZAG
 * @time: 2021/2/23 0023 10:48
 */
@Log4j2
@RestController
@RequestMapping("/upload")
@Api(value = "上传 Controller",tags = {"上传文件处理接口"})
public class UploadFileController {
    @Autowired
    BaiduService baiduService;

    @Autowired
    CommonService commonService;

    @Autowired
    QuestionBankService qbService;

    @Value("${upload.image.path}")
    String imagePath;
    @Value("${upload.record.path}")
    String recordPath;
    @Value("${upload.excel.path}")
    String excelPath;

    @PostMapping("/record/{userId}")
    @ApiOperation(value="对上传的音频进行语音识别，确认其垃圾分类")
    public R uploadRecord(@RequestParam("file")MultipartFile file, String userId){
        if (file.isEmpty()) {
            return R.failed("文件为空");
        }
        String path = commonService.handleUploadFile(file,recordPath);
        Map<String, Object> map = baiduService.apiSpeech(path,userId);
        return R.ok(map);
    }

    @PostMapping("/image/{userId}")
    @ApiOperation(value="对上传的图片进行语音识别，确认其垃圾分类")
    public R uploadImage(@RequestParam("file") MultipartFile file,String userId){
        if (file.isEmpty()) {
            return R.failed("文件为空");
        }
        String path = commonService.handleUploadFile(file,imagePath);
        Map<String, Object> map = baiduService.imageClassify(path,userId);
        return R.ok(map);
    }
}
