function getParentUntil($curNode, expression) {
    var parents = $curNode.parentsUntil(expression);
    return $(parents[parents.length - 1])
}

/**
 * 根据路径删除Json对象的属性
 * @param jsonObj Json对象
 * @param paths 需要移除的属性列表路径（嵌套的对象用".'分隔）
 */
function removeAttrByHiddenPaths(jsonObj, paths) {
    //移除不需要展示的属性
    for (i in paths) {
        var item = paths[i];
        // 该属性通过分隔符“.”，进行内嵌属性的移除。
        var attrs = item.split(".");
        // 移除对应层级下的属性
        function deleteAttr(paths, obj) {
            var curPath = paths[0];
            var restPaths = paths.slice(1);
            // 如果还有剩余的路径，说明还没有到删除的属性的那一层，需要继续递归
            if (restPaths.length > 0) {
                var curObj = obj[curPath];
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

/**
 * 根据路径删除除了配置的Json对象的其他兄弟属性
 * @param jsonObj Json对象
 * @param paths 需要保留的属性列表路径（嵌套的对象用".'分隔）
 */
function removeAttrByOnly1ShowPaths(jsonObj, paths) {
    //移除不需要展示的属性
    for (var i in paths) {
        var item = paths[i];
        // 该属性通过分隔符“.”，进行内嵌属性的移除。
        var attrs = item.split(".");
        // 移除对应层级下的属性
        function deleteAttr(paths, obj) {
            var curPath = paths[0];
            var restPaths = paths.slice(1);
            // 如果还有剩余的路径，说明还没有到删除的属性的那一层，需要继续递归
            if (restPaths.length > 0) {
                var curObj = obj[curPath];
                // 如果该对象是数组类型的，则需要对该数组的每个属性进行递归
                if (curObj instanceof Array) {
                    for (var index in curObj) {
                        deleteAttr(restPaths, curObj[index])
                    }
                } else {
                    deleteAttr(restPaths, curObj)
                }
            } else {
                // 已经是最后一层了，删除除了指定属性外的其他所有属性
                for (var attr in obj) {
                    if (attr != curPath)
                        delete obj[attr]
                }
            }
        }

        deleteAttr(attrs, jsonObj)
    }
}

function mergeColumn($table) {
    //获取两列的宽度，和并到一个列中
    var ths = $table.find("tr th");
    // 如果有5列，表示还没有合并过，进行合并，否则跳过
    if (ths.length == 5) {
        var $valueTh = $(ths[1]);
        var $dataTypeTh = $(ths[4]);
        var valueColumnWidth = Number($valueTh.css("width").replace("px", ""));
        var dataTypeColumnWidth = Number($dataTypeTh.css("width").replace("px", ""));
        // 合并th的宽度
        $valueTh.css("width", valueColumnWidth + dataTypeColumnWidth);
        $dataTypeTh.remove()
    }
}

function initHiddenMaps() {
    var result;
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

function initViewJson($curContentDivNode, json) {
    var $currentPreNode = $("pre.json", $curContentDivNode)

    var options = {
        mode: 'view',
        onError: function (err) {
            alert(err.toString());
        }
    };
    $currentPreNode.hide().after("<div class=\"jsoneditor jsoneditorViewDiv\"></div>");
    new JSONEditor($curContentDivNode.find(".jsoneditorViewDiv")[0], options, JSON.parse(json));
}

function initJsoneditor() {
    var hiddenMaps = initHiddenMaps();
    $(".snippet pre code").each(function () {
        var $currentCode = $(this);
        // 获取json数据
        var json = $currentCode.text();

        // 应为要合并两列，所以先找到要隐藏的那列对应的td标签，并隐藏
        var $dataTypeTd = getParentUntil($currentCode, "tr");

        // 过滤其他不是参数形式的 pre code 标签
        if ($dataTypeTd[0].nodeName == "TD") {
            // 隐藏对应的td标签
            $dataTypeTd.hide();
            // 找到对应的行标签
            var $tr = getParentUntil($dataTypeTd, ".operation-params");

            var $table = getParentUntil($tr, ".sandbox");
            // 合并原来的列两列为一列，每个表只需要处理一次
            mergeColumn($table);

            // create the editor
            var $originalText = $tr.find(".body-textarea");
            // 隐藏 .parameter-content-type 标签
            $originalText.next().next().hide();
            $originalText.hide().after("<div class=\"jsoneditor jsoneditorDiv\"></div>");
            var options = {
                mode: 'tree',
                modes: ['text', 'tree'], // allowed modes
                onError: function (err) {
                    alert(err.toString());
                },
                onModeChange: function (newMode, oldMode) {
                    console.log('Mode switched from', oldMode, 'to', newMode);
                }
            };
            var jsonObj = JSON.parse(json);

            // 获取当前操作的ID
            var actId = getParentUntil($table, ".operations").attr("id");

            // 去除配置的隐藏属性
            removeAttrByHiddenPaths(jsonObj, hiddenMaps[actId + "_hide"]);
            // 去除除配置的只显示的属性外的其他属性
            removeAttrByOnly1ShowPaths(jsonObj, hiddenMaps[actId + "_show"]);
            var editor = new JSONEditor($tr.find(".jsoneditorDiv")[0], options, jsonObj);

            // 添加提交的监听方法
            $table.parent().find(".submit").click(function (e) {
                $originalText.val(JSON.stringify(editor.get(), null, 2))
            })
        }
    })
}

function showResponseByJsonedit(parent, data) {

    var content = "";
    if (data.content === undefined) {
        content = data.data;
    } else {
        content = data.content.data;
    }
    var headers = data.headers;
    content = jQuery.trim(content);

    // if server is nice, and sends content-type back, we can use it
    var contentType = null;
    if (headers) {
        contentType = headers['Content-Type'] || headers['content-type'];
        if (contentType) {
            contentType = contentType.split(';')[0].trim();
        }
    }

    if (content) {
        if (contentType === 'application/json' || /\+json$/.test(contentType)) {
            // json 类型的返回值
            var $curContentDivNode = $(parent.el)
            initViewJson($curContentDivNode, content)
        }
    }
}
