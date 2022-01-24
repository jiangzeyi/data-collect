import com.alibaba.fastjson.JSONObject
import com.ivw.task.script.IScript

class TestScript implements IScript {

    @Override
    Object execute(Object data) {
        def result = JSONObject.parseObject(data as String)
        result.values().forEach(item -> {
            println(item)
        })
        result
    }
}