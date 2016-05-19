/**
 * Created by Srđan on 19.5.2016..
 */

$(function () {
    var keyValueList = window.location.search.substring(1).split('&'), params = {};
    for (var i = 0 in keyValueList) {
        if (keyValueList.hasOwnProperty(i)) {
            var keyValue = keyValueList[i].split('='); //split key and value
            params[keyValue[0]] = keyValue[1];
        }
    }

    if (params.schemaName == null || params.schemaUri || params.rootElement) {
        return;
    }

    console.log(params);

    var extractor = new Xsd2Json(params.schemaName, {
            schemaURI: params.schemaUri,
            rootElement: params.rootElement
        }),
        elementText = '<element xlmns="http://ftn.uns.ac.rs/xml"></element>'
            .split('element')
            .join(params.rootElement);

    $('#XMLEditor')
        .text(elementText)
        .xmlEditor({
            menuEntries: [],
            enforceOccurs: true,
            prependNewElements: true,
            schema: extractor.getSchema(),
            ajaxOptions: {
                xmlUploadPath: params.submitPath
            }
        });
});