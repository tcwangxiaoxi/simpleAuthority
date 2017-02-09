function mergeColumn($table) {
    //获取两列的宽度，和并到一个列中
    var ths = $table.find("tr th")
    var $valueTh = $(ths[1])
    var $dataTypeTh = $(ths[4])
    var valueColumnWidth = Number($valueTh.css("width").replace("px", ""))
    var dataTypeColumnWidth = Number($dataTypeTh.css("width").replace("px", ""))
    // 合并th的宽度
    $valueTh.css("width", valueColumnWidth + dataTypeColumnWidth)
    $dataTypeTh.remove()
}

function initJsoneditor() {
    var isMerged = false
    $(".snippet pre code").each(function (index, element) {

        var $currentCode = $(this);
        // 获取json数据
        var json = $currentCode.text();

        // 应为要合并两列，所以先找到要隐藏的那列对应的td标签，并隐藏
        var codeParents = $currentCode.parentsUntil("tr")
        var $dataTypeTd = $(codeParents[codeParents.length - 1])

        // 过滤其他不是参数形式的 pre code 标签
        if ($dataTypeTd[0].nodeName == "TD") {
            // 隐藏对应的td标签
            $dataTypeTd.hide()
            // 找到对应的行标签
            var parents = $dataTypeTd.parentsUntil(".operation-params")
            var $tr = $(parents[parents.length - 1])

            var trParents = $tr.parentsUntil(".sandbox")
            var $table = $(trParents[trParents.length - 1])
            if (!isMerged) {
                // 合并原来的列两列为一列，每个表只需要处理一次
                mergeColumn($table)
                isMerged = true
            }

            // create the editor
            var $originalText = $tr.find(".body-textarea")
            // 隐藏 .parameter-content-type 标签
            $originalText.next().next().hide()
            $originalText.hide().after("<div class=\"jsoneditor\"></div>")
            var options = {
                mode: 'tree',
                //modes: ['code', 'form', 'text', 'tree', 'view'], // allowed modes
                onError: function (err) {
                    alert(err.toString());
                }
            };
            eval("var jsonObj = " + json)
            var editor = new JSONEditor($tr.find(".jsoneditor")[0], options, jsonObj);

            // 添加提交的监听方法
            $table.parent().find(".submit").click(function (e) {
                $originalText.val(JSON.stringify(editor.get(), null, 2))
            })
        }
    })
}
