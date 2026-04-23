package com.study.yuaicodemother.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.study.yuaicodemother.module.dto.app.AppAddRequest;
import com.study.yuaicodemother.module.dto.app.AppQueryRequest;
import com.study.yuaicodemother.module.entity.App;
import com.study.yuaicodemother.module.entity.User;
import com.study.yuaicodemother.module.vo.AppVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author fxy
 * @since 2026-04-21
 */
public interface AppService extends IService<App> {

    AppVO getAppVO(App app);

    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    List<AppVO> getAppVOList(List<App> appList);

    Flux<String> chatToGenCode(Long appId, String message, User user);

    String deployApp(Long appId, User loginUser);

    void generateAppScreenshotAsync(Long appId, String appUrl);

    Long createApp(AppAddRequest appAddRequest, User loginUser);
}
