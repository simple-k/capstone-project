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

    // Grid Item 2
    // Get Request - Render School Index
    const renderSchoolIndex  = (schools) => {
        $('.loadingIndex').hide();
        schools.filter(({id, stateSchoolId, district, highGrade, schoolName, streetAddress, zipCode, phone, charter, titleISchool, title1SchoolWide, students, teachers, studentTeacherRatio}) => {
            $('.list_school').append(`
                <div class='row school'>
                    <div id='school-listing' class=''>
                        <h5 class='select_school my-1 mx-1 text-primary cursor-pointer' data-schoolId='${id}' data-schoolAddress='${streetAddress}${zipCode}'>${schoolName}</h5>
                        <div class="flex-container mx-1">
                            <div class=''>
                                <div onload="placeMarker('${streetAddress} ${zipCode}')"></div>
                                <p>${streetAddress}</p>
                                <p><span>San Antonio, TX, </span><span>${zipCode}</span></p>
                                <p>${phone}</p>
                                <p>${district.name}</p>
                            </div>
                            <div class='isd-logo'>
                                <a href='${district.url}' target="_blank">
                                    <img src='${district.image}' />
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        })
    };

    //Grid Item 3
    // Get Request - Render Selected School
    const renderSelectedSchool  = (school) => {
        let transportation = 'no', disability = 'no', daycare = 'no', financial = 'no', language = 'no';
        if (school.transportationService) {
            transportation = 'yes';
        }
        if (school.disabilityService) {
            disability = 'yes';
        }
        if (school.daycareService) {
            daycare = 'yes';
        }
        if (school.financialService) {
            financial = 'yes';
        }
        if (school.languageService) {
            language = 'yes';
        }
        $('.loadingIndex').hide();
        $('#selected_school').append(`
            <div id='school'>
                <h5 class="my-1 mx-1">${school.schoolName}</h5>
                <div id='school-details-container'>
                    <div class="school-details-1 mx-1">
                        <p>${school.streetAddress}</p>
                        <p>San Antonio, TX, <span>${school.zipCode}</span></p>
                        <p>${school.phone}</p>
                        <p>District: <a href='${school.district.url}' target='_blank'><span>${school.district.name}</span></a></p>
                    </div>
                    <div class="school-details-2 mx-1">
                        <p>Grades: PK-<span>${school.highGrade}</span></p>
                        <p>Total students enrolled: <span>${school.students}</span></p>
                        <p>Total teachers: <span>${school.teachers}</span></p>
                        <p>Students per teacher: <span>${school.studentTeacherRatio}</span></p>
                    </div>
                </div>
                <div class="my-1 mx-1">
                    <p>Transportation service :<span> ${transportation}</span></p>
                    <p>Daycare service: <span> ${daycare}</span></p>
                    <p>Financial service: <span> ${financial}</span></p>
                    <p>Disability service: <span> ${disability}</span></p>
                    <p>Language service: <span> ${language}</span></p>
                </div>
            </div>
        `);
        $('#selected_school').on('click', "#findDirection" , e => {
            calculateAndDisplayRoute(`${school.streetAddress}, ${school.zipCode}`);
        });


    };


    // Render School Index
    // getSchoolJson().then(renderSchoolIndex).catch(err);

    // Render Selected School and Recenter the map over the address On Click
    $('.list_school').on('click', ".select_school" ,(e) => {
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
        $('.school').remove();
        $('.grid-item-1 .loadingIndex').show();
        getSchoolJson().then(renderSchoolIndex)
            .then(() => $("#resetIndex").prop("disabled", false)).catch(err);
    });

    // Filter School By District On Select Option
    $('#search-district').on('input', () => {
        $('.school').remove();
        $('.grid-item-1 .loadingIndex').show();
        if($('#search-district option:selected').val() === `search by district...`){
            getSchoolJson().then(renderSchoolIndex).catch(err);
        } else {
            let districtId = $('#search-district option:selected').val();
            console.log(districtId);
            getSelectedDistrictJson(districtId).then(renderSchoolIndex).catch(err);
        }
    });

    // Filter School By School Name on Search Bar
    $('#search-school-name').on('input', () => {
        getSchoolJson().then(schools => {
            $('.list_school').empty();
            $('.grid-item-1 .loadingIndex').show();
            schools.map(school => {
                $('#search-school-name').val().split(" ").map(word => {
                    let id = school.id, stateSchoolId = school.stateSchoolId, district = school.district, highGrade = school.highGrade, schoolName = school.schoolName, streetAddress = school.streetAddress, zipCode = school.zipCode, phone = school.phone, charter = school.charter, titleISchool = school.titleISchool, title1SchoolWide = school.title1SchoolWide, students = school.students, teachers = school.teachers, studentTeacherRatio = school.studentTeacherRatio;

                        if(schoolName.toLowerCase().indexOf(word.toLowerCase()) !== -1){
                            console.log(word);
                            $('.list_school').append(`
                                <div class='row school'>
                                    <div id='school-listing' class=''>
                                        <h5 class='select_school my-1 mx-1 text-primary cursor-pointer' data-schoolId='${id}' data-schoolAddress='${streetAddress}${zipCode}'>${schoolName}</h5>
                                        <div class="flex-container mx-1">
                                            <div class=''>
                                                <div onload="placeMarker('${streetAddress} ${zipCode}')"></div>
                                                <p>${streetAddress}</p>
                                                <p><span>San Antonio, TX, </span><span>${zipCode}</span></p>
                                                <p>${phone}</p>
                                                <p>${district.name}</p>
                                            </div>
                                            <div class='isd-logo'>
                                                <a href='${district.url}' target="_blank">
                                                    <img src='${district.image}' />
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            `);
                        }
                })
            })
        });
    });

});// Ready