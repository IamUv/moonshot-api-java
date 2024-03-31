package com.iamuv.moonshot.api.domain.service;

import com.iamuv.moonshot.api.domain.model.Api;
import com.iamuv.moonshot.api.infrastructure.dto.*;
import com.iamuv.moonshot.api.infrastructure.exception.MoonshotApiError;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ApiService {

    /**
     * 计算Token
     * <p>
     * 请求地址：POST https://api.moonshot.cn/v1/tokenizers/estimate-token-count
     *
     * @param token
     * @param model
     * @param messages
     * @return total_tokens的数值 {
     * "data": {
     * "total_tokens": 80
     * }
     * }
     * @throws IOException          通讯超时
     * @throws InterruptedException 通讯中断
     * @throws MoonshotApiError     业务异常
     */
    int estimateTokenCount(
            String token,
            Api.ApiModel model,
            List<Api.Message> messages) throws IOException, InterruptedException, MoonshotApiError;

    /**
     * Chat Completion
     * <p>
     * 请求地址：POST https://api.moonshot.cn/v1/chat/completions
     *
     * @param token    API Key
     * @param messages 这是一个结构体的列表，每个元素类似如下：{"role": "user", "content": "你好"} role 只支持 system,user,assistant 其一，content 不得为空
     *                 包含迄今为止对话的消息列表
     * @param model    目前是 moonshot-v1-8k,moonshot-v1-32k,moonshot-v1-128k 其一
     *                 Model ID, 可以通过 List Models 获取
     * @return {
     * "id": "cmpl-04ea926191a14749b7f2c7a48a68abc6",
     * "object": "chat.completion",
     * "created": 1698999496,
     * "model": "moonshot-v1-8k",
     * "choices": [
     * {
     * "index": 0,
     * "message": {
     * "role": "assistant",
     * "content": " 你好，李雷！1+1等于2。如果你有其他问题，请随时提问！"
     * },
     * "finish_reason": "stop"
     * }
     * ],
     * "usage": {
     * "prompt_tokens": 19,
     * "completion_tokens": 21,
     * "total_tokens": 40
     * }
     * }
     * @throws IOException          通讯超时
     * @throws InterruptedException 通讯中断
     * @throws MoonshotApiError     业务异常
     */
    ChatCompletionDto chatCompletions(String token, List<Api.Message> messages, Api.ApiModel model) throws IOException, InterruptedException, MoonshotApiError;

    /**
     * Chat Completion
     * <p>
     * 请求地址：POST https://api.moonshot.cn/v1/chat/completions
     *
     * @param token            API Key
     * @param messages         这是一个结构体的列表，每个元素类似如下：{"role": "user", "content": "你好"} role 只支持 system,user,assistant 其一，content 不得为空
     *                         包含迄今为止对话的消息列表
     * @param model            目前是 moonshot-v1-8k,moonshot-v1-32k,moonshot-v1-128k 其一
     *                         Model ID, 可以通过 List Models 获取
     * @param maxTokens        这个值建议按需给个合理的值，如果不给的话，我们会给一个不错的整数比如 1024。特别要注意的是，这个 max_tokens 是指您期待我们返回的 token 长度，而不是输入 + 输出的总长度。比如对一个 moonshot-v1-8k 模型，它的最大输入 + 输出总长度是 8192，当输入 messages 总长度为 4096 的时候，您最多只能设置为 4096，否则我们服务会返回不合法的输入参数（ invalid_request_error ），并拒绝回答。如果您希望获得“输入的精确 token 数”，可以使用下面的“计算 Token” API 使用我们的计算器获得计数
     *                         聊天完成时生成的最大 token 数。如果到生成了最大 token 数个结果仍然没有结束，finish reason 会是 "length", 否则会是 "stop"
     * @param temperature      如果设置，值域须为 [0, 1] 我们推荐 0.3，以达到较合适的效果
     *                         使用什么采样温度，介于 0 和 1 之间。较高的值（如 0.7）将使输出更加随机，而较低的值（如 0.2）将使其更加集中和确定性
     * @param topP             默认 1.0
     *                         另一种采样方法，即模型考虑概率质量为 top_p 的标记的结果。因此，0.1 意味着只考虑概率质量最高的 10% 的标记。一般情况下，我们建议改变这一点或温度，但不建议 同时改变
     * @param n                默认为 1，不得大于 5。特别的，当 temperature 非常小靠近 0 的时候，我们只能返回 1 个结果，如果这个时候 n 已经设置并且 > 1，我们的服务会返回不合法的输入参数(invalid_request_error)
     *                         为每条输入消息生成多少个结果
     * @param presencePenalty  默认为 0
     *                         存在惩罚，介于-2.0到2.0之间的数字。正值会根据新生成的词汇是否出现在文本中来进行惩罚，增加模型讨论新话题的可能性
     * @param frequencyPenalty 默认为 0
     *                         频率惩罚，介于-2.0到2.0之间的数字。正值会根据新生成的词汇在文本中现有的频率来进行惩罚，减少模型一字不差重复同样话语的可能性
     * @param stop             默认 null
     *                         停止词，当全匹配这个（组）词后会停止输出，这个（组）词本身不会输出。最多不能超过 5 个字符串，每个字符串不得超过 32 字节
     * @return {
     * "id": "cmpl-04ea926191a14749b7f2c7a48a68abc6",
     * "object": "chat.completion",
     * "created": 1698999496,
     * "model": "moonshot-v1-8k",
     * "choices": [
     * {
     * "index": 0,
     * "message": {
     * "role": "assistant",
     * "content": " 你好，李雷！1+1等于2。如果你有其他问题，请随时提问！"
     * },
     * "finish_reason": "stop"
     * }
     * ],
     * "usage": {
     * "prompt_tokens": 19,
     * "completion_tokens": 21,
     * "total_tokens": 40
     * }
     * }
     * @throws IOException          通讯超时
     * @throws InterruptedException 通讯中断
     * @throws MoonshotApiError     业务异常
     */
    ChatCompletionDto chatCompletions(String token, List<Api.Message> messages, Api.ApiModel model, Integer maxTokens, Float temperature, Float topP, Integer n,
                                      Float presencePenalty, Float frequencyPenalty, List<String> stop) throws IOException, InterruptedException, MoonshotApiError;


    List<ModelDto> models(String token) throws IOException, InterruptedException, MoonshotApiError;


    FileDto uploadFile(String token, Path filePath) throws IOException, InterruptedException, MoonshotApiError;

    FileDeleteDto deleteFile(String token, String id) throws IOException, InterruptedException, MoonshotApiError;

    FileDto getFileInfo(String token, String id) throws IOException, InterruptedException, MoonshotApiError;

    FileContentDto getFileContent(String token, String id) throws IOException, InterruptedException, MoonshotApiError;

    List<FileDto> getFileList(String token) throws MoonshotApiError, IOException, InterruptedException;
}
