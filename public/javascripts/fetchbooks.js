function fetchBooks(page){
    $.ajax({
        url: "/books/booklist?page="+page,
        method: "GET",
        success: function(response){
            $("#books").html(response);
            //i probably shouldn't do this?
            $("[name=page_link]").click(function(event){
                var page=$(event.target).data("pagenum");
                fetchBooks(page);
             });
        }
    });
}

$(document).ready(function(){
    $("[name=page_link]").click(function(event){
    console.log("clicked");
        var page=$(event.target).data("pagenum");
        fetchBooks(page);
    });
});