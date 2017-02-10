function getParentUntil($curNode, expression) {
    var parents = $curNode.parentsUntil(expression)
    return $(parents[parents.length - 1])
}

/**
 * 根据路径删除Json对象的属性
 * @param paths 路径（嵌套的对象用".'分隔）
 * @param jsonObj Json对象
 */
function removeAttrByPaths(jsonObj, paths) {
    //移除不需要展示的属性
    for (i in paths) {
        var item = paths[i]
        // 该属性通过分隔符“.”，进行内嵌属性的移除。
        var attrs = item.split(".")
        // 移除对应层级下的属性
        function deleteAttr(paths, obj) {
            var curPath = paths[0]
            var restPaths = paths.slice(1)
            // 如果还有剩余的路径，说明还没有到删除的属性的那一层，需要继续递归
            if (restPaths.length > 0) {
                var curObj = obj[curPath]
                // 如果该对象是数组类型的，则需要对该数组的每个属性进行递归
                if (curObj instanceof Array) {
                    for (index in curObj) {
                        deleteAttr(restPaths, curObj[index])
                    }
                } else {
                    deleteAttr(restPaths, curObj)
                }
            } else {
                // 已经是最后一层了，删除属性
                delete obj[curPath]
            }
        }
        deleteAttr(attrs, jsonObj)
    }
}

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

function initHiddenMaps() {
    var result
    jQuery.ajax({
        type: "get",
        async: false,
        url: "/swaggerEx/hiddenMaps",
        dataType: "json",
        success: function (data) {
            result = data;
        },
        error: function (err) {
            alert(err);
        }
    });
    return result
}

/**
 *
 * 通过过滤项移除不需要展示的属性
 *
 * @param paths 需要移除的属性列表路径
 * @param jsonObj 当前的参数对象
 */
function filterByHiddenPaths(paths, jsonObj) {
    //根据路径移除不需要展示的属性（嵌套的分隔符为“.”）
    for (i in paths) {
        var path = paths[i]
        // 该属性通过分隔符“.”，进行内嵌属性的移除。
        var attrs = item.split(".")
        // 移除对应层级下的属性

    }
}

function initJsoneditor(data) {
    var isMerged = false
    var hiddenMaps = initHiddenMaps()
    $(".snippet pre code").each(function (index, element) {

        var $currentCode = $(this);
        // 获取json数据
        var json = $currentCode.text();

        // 应为要合并两列，所以先找到要隐藏的那列对应的td标签，并隐藏
        var $dataTypeTd = getParentUntil($currentCode, "tr")

        // 过滤其他不是参数形式的 pre code 标签
        if ($dataTypeTd[0].nodeName == "TD") {
            // 隐藏对应的td标签
            $dataTypeTd.hide()
            // 找到对应的行标签
            var $tr = getParentUntil($dataTypeTd, ".operation-params")

            var $table = getParentUntil($tr, ".sandbox")
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
            var jsonObj = JSON.parse(json)

            // 获取当前操作的ID
            var actId = getParentUntil($table, ".operations").attr("id")

            removeAttrByPaths(hiddenMaps[actId], jsonObj)
            var editor = new JSONEditor($tr.find(".jsoneditor")[0], options, jsonObj);

            // 添加提交的监听方法
            $table.parent().find(".submit").click(function (e) {
                $originalText.val(JSON.stringify(editor.get(), null, 2))
            })
        }
    })
}
