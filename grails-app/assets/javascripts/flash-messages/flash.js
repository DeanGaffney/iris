/**
 * Created by dean on 20/10/17.
 */

function displayFlashMessage(flashType, message){

    const type = getTypeClass(flashType);

    $("#flash-message").append("<div id='flash-alert' class='alert "  + type + "' alert-dismissible fade show' role='alert'>" +
        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
        "<span aria-hidden='true'>&times;</span></button><strong>" + message + "</strong></div>");
}

function getTypeClass(flashType){
    var alertClass;
    if(flashType == "success"){
        alertClass = "alert-success";
    }else if(flashType == "info"){
        alertClass = "alert-info";
    }else if(flashType == "warning"){
        alertClass = "alert-warning";
    }else if(flashType == "danger"){
        alertClass = "alert-danger";
    }
    return alertClass;
}
