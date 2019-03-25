"use strict";

$(document).ready(() => {

    // CSRF token
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    // Get Request - All School Data in JSON
    const getSchoolJson = () => {
        return fetch('/api/school/all')
            .then(response => response.json());
    };

    // Get Request - Selected School Data in JSON
    const getSelectedSchoolJson = (schoolId) => {
        return fetch('/api/school/' + schoolId)
            .then(response => response.json());
    };

    // Get Request - Selected District Data in JSON
    const getSelectedDistrictJson = (districtId) => {
        return fetch('/api/district/' + districtId)
            .then(response => response.json());
    };

    // Error Handler
    const err = (error) => {
        console.log('Oh no! Something went wrong.\nCheck the console for details.');
        console.log(error);
    };

    // Get Request - Render School Index
    const renderSchoolIndex  = (schools) => {
        $('.loadingIndex').hide();
        schools.filter(({id, stateSchoolId, district, highGrade, schoolName, streetAddress, zipCode, phone, charter, titleISchool, title1SchoolWide, students, teachers, studentTeacherRatio}) => {
            $('#school-listing-scroll-box').append(`
                <div class='list_school'>
                    <div class='school'>
                        <div id='school-listing' class='text-left row mx-auto px-1'>
                            <h5 class='select_school mt-1 text-primary cursor-pointer' data-schoolId='${id}' data-schoolAddress='${streetAddress}${zipCode}'>${schoolName}</h5>
                            <div class='flex-container mb-1'>
                                <div onload="placeMarker('${streetAddress} ${zipCode}')">
                                    <p>${streetAddress}</p>
                                    <p><span>San Antonio, TX, </span><span>${zipCode}</span></p>
                                    <p>${phone}</p>
                                </div>
                                <div class='text-center'>
                                    <a href='${district.url}' target="_blank">
                                        <img src='${district.image}' />
                                    </a>
                                    <p>${district.name}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        })
    };

    // Get Request - Render Selected School
    const renderSelectedSchool  = (school) => {
        $('.loadingIndex').hide();
        $('#selected_school').append(`
            <div id='school'>
                <h3>${school.schoolName}</h3>
                <p>${school.streetAddress}</p>
                <p>San Antonio, TX, <span>${school.zipCode}</span></p>
                <p>Grades: PK-<span>${school.highGrade}</span></p>
                <p>Total students enrolled: <span>${school.students}</span></p>
                <p>Total teachers: <span>${school.teachers}</span></p>
                <p>Students per teacher: <span>${school.studentTeacherRatio}</span></p>
                <p>District: <a href='${school.district.url}' target='_blank'><span>${school.district.name}</span></a></p>
            </div>
        `);
    };

    // Render School Index
    // getSchoolJson().then(renderSchoolIndex).catch(err);

    // Render Selected School and Recenter the map over the address On Click
    $('#school-listing-scroll-box').on('click', ".select_school" ,(e) => {
        let schoolId = $(e.target).data('schoolid');
        let schoolAddress = $(e.target).data('schooladdress');
        $("#school").remove();
        $('.grid-item-3 .loadingIndex').show();
        getSelectedSchoolJson(schoolId).then(renderSelectedSchool).catch(err);
        locate(schoolAddress);
    });

    // Reset School Index On Click
    $('#resetIndex').click(() => {
        $("#resetIndex").prop("disabled", true);
        $("#search-form")[0].reset();
        $('.list_school').remove();
        $('.grid-item-1 .loadingIndex').show();
        getSchoolJson().then(renderSchoolIndex)
            .then(() => $("#resetIndex").prop("disabled", false)).catch(err);
    });

    // Filter School By District On Select Option
    $('#search-district').on('input', () => {
        $('.list_school').remove();
        $('.grid-item-1 .loadingIndex').show();
        if($('#search-district option:selected').val() === `search by district...`){
            getSchoolJson().then(renderSchoolIndex).catch(err);
        } else {
            let districtId = $('#search-district option:selected').val();
            console.log(districtId);
            getSelectedDistrictJson(districtId).then(renderSchoolIndex).catch(err);
        }
    });

});// Ready