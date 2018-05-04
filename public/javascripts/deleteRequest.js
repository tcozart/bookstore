function sendDeleteRequest(url,redirect,token){
    $.ajax({
        url: url,
        method: "DELETE",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
            "Csrf-Token": token
        },
        success: function(){
            window.location=redirect
        },
        error: function(){
            window.location.reload();
        }
    });
}
$(document).ready(function(){
    $("#delete").click(function(){
    var id=$("#delete").data("status");
    var token=$("[name=csrfToken]").val();
    console.log(token);
    if (confirm("Delete? This action can not be undone."))
        sendDeleteRequest("/books/"+id,"/books",token);
    });
});
