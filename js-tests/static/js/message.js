$(function () {
    $('#sendMessage').click(() => {
        const text = $('#messageContent').val();
        $.ajax({
            url: "/message",
            method: 'POST',
            data: { text },
            dataType: "json"
        }).done(data => {
            console.log(data)
        })
    })
})