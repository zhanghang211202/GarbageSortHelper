package top.miyamoto.service;

import top.miyamoto.common.DemoException;

import java.util.Map;

/**
 * @description:
 * @author: ZAG
 * @time: 2021/2/23 0023 10:50
 */
public interface BaiduService {
    Map<String, Object> apiSpeech(String fileName,String userId);
    Map<String, Object> imageClassify(String fileName,String userId);
}
