$(document).ready(function (event) {

    // function jsonDate() {
    //
    // }
    let create_data;
    let create_data_tmp;
    let tabl_arr = [];
    let arr_post = [];
    // let requestURL = 'data_table.json';
    // var request = new XMLHttpRequest();
    // request.open('GET', requestURL);
    // request.open('POST', requestURL);
    // console.log("request1: ", request);
    // request.responseType = 'json';
    // console.log("request2: ", request);
    // request.send();
    get__ajax__submit();


    $("#btn-add").click(function () {
        $("#myModal").modal({show: true});
    });
    $("#btn-save").click(function () {
        var create_data = {}
        create_data["created"] = $("#created").val();
        create_data["name"] = $("#name").val();
        create_data["counts"] = $("#counts").val();
        console.log("Result create : ",  create_data);
        arr_post = no_rpt_arr(tabl_arr, create_data);
        // Траблы!!!
        console.log("Result ARR : ", arr_post);
        if (arr_post.length>0){
            $(arr_post).each(function (index, item) {
                post__ajax__submit(item);
            });
        }
        // post__ajax__submit(create_data);
        $("#myModal").modal({show: false});
        tableRecoin();
    });
    $("#btn-close").click(function () {
        get__ajax__submit();
        $("#myModal").modal({show: false});
        tableRecoin();
    });
    $("#btn-x").click(function () {
        get__ajax__submit();
        $("#myModal").modal({show: false});

        tableRecoin();
    });

    $("#btn-addList").click(function () {
        arr_post = [];
        tableRecoin();
        let arr_tmp = jsonArr;
        arr_post = no_rpt_arr(tabl_arr, arr_tmp);
        console.log("Result ARR : ", arr_post);
        if (arr_post.length>0){
            $(arr_post).each(function (index, item) {
                post__ajax__submit(item);
            });
        }
        arr_post = [];
    });

    $('#create-form2').on("click", '#btn-edit', function () {
        let id = $(this).val();
        create_data = {}
        create_data["id"] = $("#id_input_" + id).val();
        create_data["created"] = $("#created_input_" + id).val();
        create_data["name"] = $("#name_input_" + id).val();
        create_data["counts"] = $("#counts_input_" + id).val();
        console.log("Submit edit  inp: ", JSON.stringify(create_data) + ', ' + id);
        //защита от дубликатов
        if (!_.isEqual(create_data, create_data_tmp)){
            put__ajax__submit(create_data, id);
            tableRecoin();
            create_data_tmp = 0;
        }
    });

    $('#create-form2').on("click", '#btn-del', function () {
        let id = $(this).val();
        console.log("Submit del : ", id);
        if (id > 0) {
            del__ajax__submit(id);
            tableRecoin();
        }
    });

    $('#receipts').hover(
        function () {
            tabl_arr = [];
            $('.edit_tr').hover(
                function () {
                    let ID = $(this).attr('id');
                    create_data_tmp = {}
                    create_data_tmp["id"] = $("#id_" + ID).text();
                    create_data_tmp["created"] = $("#created_" + ID).text();
                    create_data_tmp["name"] = $("#name_" + ID).text();
                    create_data_tmp["counts"] = $("#counts_" + ID).text();
                    // console.log("Index HOVER : ", JSON.stringify(create_data_tmp) + ', ' +ID);
                }
            ).change();
            $('.edit_tr').each(function (index, item) {
                tabl_arr.push(item);
            });
        }
    );

    $('#receipts').on('mouseover', 'tr', function () {
        let ID = $(this).attr('id');
        create_data_tmp = {}
        create_data_tmp["id"] = $("#id_" + ID).text();
        create_data_tmp["created"] = $("#created_" + ID).text();
        create_data_tmp["name"] = $("#name_" + ID).text();
        create_data_tmp["counts"] = $("#counts_" + ID).text();
        // console.log("MOUSEOVER HOVER : ", JSON.stringify(create_data_tmp) + ', ' +ID);
    }).change();

    $('#receipts').on('click', 'tr', function () {
        let ID = $(this).attr('id');
        console.log("TR : ", ID);
        $("#created_" + ID).hide();
        $("#name_" + ID).hide();
        $("#counts_" + ID).hide();
        $("#created_input_" + ID).show();
        $("#name_input_" + ID).show();
        $("#counts_input_" + ID).show();
    }).change();

// Edit input box click action
    $(".editbox").mouseup(function () {
        return false;
    });
    $(".edit_tr").mouseup(function () {
        console.log("Btn del : ");
    });
// Outside click action
    $(document).mouseup('tr', function () {
        $(".editbox").hide();
        $(".text").show();
    });

    function tableRecoin() {
        tabl_arr = [];
        $('.edit_tr').each(function (index, item) {
            let data_tmp;
            let ID = $(item).attr('id');
            console.log("Id:",  ID);
            data_tmp = {}
            data_tmp["name"] = $("#name_" + ID).text();
            data_tmp["created"] = $("#created_" + ID).text();
            data_tmp["counts"] = parseInt($("#counts_" + ID).text());
            tabl_arr.push(data_tmp);
        });
        console.log("Data TMP:",  tabl_arr);
        // get__ajax__submit();
    }

    function no_rpt_arr(array, data_json){
        for (let i = 0; i < data_json.length; i++) {
            let tmp = data_json[i];
            let repeat = array.some(function(element) {
                let a = JSON.stringify(element);
                let b = JSON.stringify(tmp);
                console.log("Data ELEMENT: ", a);
                console.log("Data tmp FIND:", b);
                return _.isEqual(a, b);
            });
            console.log("Result Tabl : ", repeat);
            if (!repeat){
                arr_post.push(tmp);
                console.log("Array to post:", arr_post);
            }
        };
        return arr_post;
    }

});

    function post__ajax__submit(url) {
        let dataUrl;

        if (url != null) {
            dataUrl = JSON.stringify(url);
            $("#btn-create").prop("disabled", true);

            localStorage.setItem("data_table", (url));
            localStorage.setItem("data_table.json", (url));
            console.log("POST!", dataUrl);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "api/v0/data",
                data: dataUrl,
                dataType: 'json',
                processData: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    // alert("Data upload POST ;)", JSON.stringify(data));
                    var json = "<h4>Ajax Response</h4><pre>"
                        + JSON.stringify(data, null, 4) + "</pre>";
                    $('#feedback').html(json);
                    $('#data').html(data);
                    tableDraw(data);
                    console.log("SUCCESS : ", data);
                    $("#btn-create").prop("disabled", false);
                },
                error: function (e) {
                    // alert("Data upload not success! = ");
                    var json = "<h4>Ajax Response</h4><pre>"
                        + e.responseText + "</pre>";
                    $('#feedback').html(json);
                    console.log("ERROR : ", e);
                    $("#btn-create").prop("disabled", false);
                }
            });
        }
    }

    function put__ajax__submit(create_data, index) {
        if (index > 0) {
            console.log("PUT!", create_data);
            $("#btn-edit").prop("disabled", true);
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "api/v0/data/" + index,
                data: JSON.stringify(create_data),
                dataType: 'json',
                processData: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    // alert("Data upload PUT ;)", JSON.stringify(data));
                    var json = "<h4>Ajax Response</h4><pre>"
                        + JSON.stringify(data, null, 4) + "</pre>";
                    $('#feedback').html(json);
                    $('#data').html(data);
                    tableDraw(data);
                    console.log("SUCCESS : ", data);
                    $("#btn-edit").prop("disabled", false);
                },
                error: function (e) {
                    // alert("Data upload not success! = ");
                    var json = "<h4>Ajax Response</h4><pre>"
                        + e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);
                    $("#btn-edit").prop("disabled", false);

                }
            });
        }
    }

    function del__ajax__submit(id) {
        if (id > 0) {
            console.log("DEL!", id);
            $("#btn-del").prop("disabled", true);
            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "api/v0/data/" + id,
                data: JSON.stringify(id),
                dataType: 'json',
                processData: false,
                cache: false,
                timeout: 600000,
                success: function (data) {
                    // alert("Data DEL ;)", JSON.stringify(data));
                    var json = "<h4>Ajax Response</h4><pre>"
                        + JSON.stringify(data, null, 4) + "</pre>";
                    $('#feedback').html(json);
                    $('#data').html(data);
                    tableDraw(data);
                    console.log("SUCCESS : ", data);
                    $("#btn-del").prop("disabled", false);
                },
                error: function (e) {
                    // alert("Data upload not success! = ");
                    var json = "<h4>Ajax Response</h4><pre>"
                        + e.responseText + "</pre>";
                    $('#feedback').html(json);

                    console.log("ERROR : ", e);
                    $("#btn-del").prop("disabled", false);
                }
            });
        }
    }

    function get__ajax__submit() {
        console.log("GET!");
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "api/v0/data",
            data: JSON.stringify(),
            dataType: 'json',
            processData: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                // alert("Data upload ;)", JSON.stringify(data));
                var json = "<h4>Ajax Response</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);
                $('#data').html(data);
                tableDraw(data);
                console.log("SUCCESS : ", data);
            },
            error: function (e) {
                // alert("Data upload not success! = ");
                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);

                console.log("ERROR : ", e.responseText);
            }
        });
    }

// function responseHandler(response) {
// response = $.parseJSON(response);
//     $.each(response, function(i, item) {
//         var $tr = $('<tr>').append(
//             $('<td>').text(item.name),
//             $('<td>').text(item.created),
//             $('<td>').text(item.counts)
//         ); //.appendTo('#records_table');
//         console.log($tr.wrap('<p>').html());
//     });
// }

function tableDraw(data) {
    $('#receipts tbody').empty();
    var $row = $('<tr class="edit_th">' +
        '<th>Id</th>' +
        '<th>Name</th>' +
        '<th>Created</th>' +
        '<th>Count</th>' +
        '<th>Edit</th>' +
        '<th>Delete</th>' +
        '</tr>');
    $('#receipts tbody').append($row);
    $(data).each(function (index, item) {
        let parts = item.created.split('-');
        // console.log('Data Time:', item.created);
        let dTime = new Date(item.created);
        // var options = {
        //     era: false,
        //     year: 'numeric',
        //     month: 'numeric',
        //     day: 'numeric',
        //     weekday: false,
        //     timezone: false,
        //     hour: false,
        //     minute: false,
        //     second: false
        // };
        console.log(item);
        var $row = $('<tr id="' + item.id + '" class="edit_tr">' +
            '<td class="edit_td_id"><span id="id_' + item.id + '" class="text" >'+ item.id +'</span>' +
            '<input type="number" value="' + item.id + '" class="editbox" id="id_input_' + item.id + '"/></td>' +
            '<td class="edit_td"><span id="name_' + item.id + '" class="text">' + item.name + '</span>' +
            '<input type="text" value="' + item.name + '" class="editbox" id="name_input_' + item.id + '"/></td>' +
            '<td class="edit_td">' +
            '<span type="data" id="created_' + item.id + '" class="text" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}">' + item.created + '</span>' +
            '<input type="data" value="' + item.created + '" class="editbox" id="created_input_' + item.id + '" name="created" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}"/>' +
            '</td>' +
            '<td class="edit_td_con">' +
            '<span id="counts_' + item.id + '" class="text">' + item.counts + '</span>' +
            '<input type="number" value="' + item.counts + '" class="editbox" id="counts_input_' + item.id + '"/>' +
            '</td>' +
            '<td class="edit_td_btn">' +
            '<button  type="submit" class="btn btn-primary btn-sm" id="btn-edit" value="' + item.id + '" ><img width="25" height="25" border="1"  src="images/edit.png"/></button></td>' +
            '<td class="edit_td_btn">' +
            ' <button  type="submit" class="btn btn-primary btn-sm" id="btn-del" value="' + item.id + '" ><img width="25" height="25" border="1"  src="images/x.png"/></button></td>' +
            '</tr>'); // создаем tr
        // $("#created_input_"+ item.id).val(dTime);
        // $("#created_input_"+ item.id).attr('type', 'date');
        $('#receipts tbody').append($row);
    });
}


let jsonArr =
    [
        {
            "name": "Газпром",
            "created": "2019-01-01",
            "counts": 2000
        },
        {
            "name": "Автоваз",
            "created": "2019-01-01",
            "counts": 2500
        },
        {
            "name": "Сбербанк",
            "created": "2019-01-05",
            "counts": 10000
        },
        {
            "name": "Газпром",
            "created": "2019-01-10",
            "counts": 2500
        },
        {
            "name": "Автоваз",
            "created": "2019-10-07",
            "counts": 2100
        }
    ];