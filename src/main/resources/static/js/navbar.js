"use strict";

$(document).ready(() => {

    $('.nav-link-toggle-c').click(() => {
        $('.nav-items-c').toggleClass('nav-toggle-show-c');
    });

    $('a.logout').click(() => {
        document.forms["logout"].submit();
    });

});// Ready