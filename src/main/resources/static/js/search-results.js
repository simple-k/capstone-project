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
        let no = '<i class="fas fa-times-circle text-danger pr-2"></i>';
        let yes = '<i class="fas fa-check-circle text-success pr-2"></i>';
        let transportation = no, disability = no, daycare = no, financial = no, language = no;
        let transportationLink ='', daycarePdf = '', languagePdf = '', disabilityPdf = '', financialPdf = '', enrollLink = '', calendarPdf = '', immunizationsPdf = '', mobileAppLink = '';
        let teachers = Math.round(parseFloat(school.teachers));
        if (school.transportationService) {
            transportation = yes;
        }
        if (school.district.name == 'SAN ANTONIO ISD' && transportation == yes) {
            transportationLink = '<a href="https://www.infofinderi.com/ifi/?cid=SAI2PDRU8K2M" target="_blank">Bus Routes</a>'
        };
        if (school.disabilityService) {
            disability = yes;
        }
        if (school.district.name == 'SAN ANTONIO ISD' && disability == yes) {
            disabilityPdf = '<a href="/doc/tea_special_ed.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a>'
        };
        if (school.daycareService) {
            daycare = yes;
        }
        if (school.district.name == 'SAN ANTONIO ISD' && daycare == yes) {
            daycarePdf = '<a href="/doc/saisd/daycare.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a><a href="/doc/challenge_fees.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a>'
        };
        if (school.financialService) {
            financial = yes;
        }
        if (school.district.name == 'SAN ANTONIO ISD' && financial == yes) {
            financialPdf = '<a href="/doc/saisd/CEP_food.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a><a href="/doc/Pre-K4SAGrant.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a>'
        };
        if (school.languageService) {
            language = yes;
        }
        if (school.district.name == 'SAN ANTONIO ISD' && language == yes) {
            languagePdf = '<a href="/doc/saisd/language.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a><a href="/doc/saisd/language_spanish.pdf" target="_blank"><i class="far fa-file-pdf text-danger"></i></a>'
        };
        if (school.district.name == 'SAN ANTONIO ISD') {
            calendarPdf = '<a href="/doc/saisd/calendar.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a>'
        };
        if (school.district.name == 'SAN ANTONIO ISD') {
            enrollLink = '<a href="https://saisdenroll.schoolmint.net/signin" target="_blank">Enroll</a>'
        };
        if (school.district.name == 'SAN ANTONIO ISD') {
            immunizationsPdf = '<a href="/doc/saisd/immunizations.pdf" target="_blank"><i class="far fa-file-pdf text-danger pr-2"></i></a>'
        };
        if (school.district.name == 'SAN ANTONIO ISD') {
            mobileAppLink = '<a href="https://www.saisd.net/main/index.php?option=com_content&view=article&id=3685:saisd-launches-mobile-app-get-it-now&catid=3:news&Itemid=151" target="_blank">Apps</a>'
        };
        $('.loadingIndex').hide();
        $('#selected_school').append(`
            <div id='school'>
                <h5 class="my-1 mx-1">${school.schoolName}</h5>
                <div class='school-details-container'>
                    <div class="school-details-1 mx-1">
                        <p>${school.streetAddress}</p>
                        <p>San Antonio, TX, <span>${school.zipCode}</span></p>
                        <p>${school.phone}</p>
                    </div>
                    <div class="school-details-2 mx-1">
                        <p>Grades: PK-<span>${school.highGrade}</span></p>
                        <p>Total students enrolled: <span>${school.students}</span></p>
                        <p>Total teachers: <span>${teachers}</span></p>
                        <p>Students per teacher: <span>${school.studentTeacherRatio}</span></p>
                    </div>
                    <div class="school-details-3 mx-1">
                        <p>District: <span class="pr-2"><img src="${school.district.image}" alt="district logo" height="16px" style="vertical-align: baseline; padding-top: 2px"/></span><a href='${school.district.url}' target='_blank'><span>${school.district.name}</span></a></p>
                        <p>Pre-k enrollment: <span>${enrollLink}</span></p>
                        <p>Calendar: <span> ${calendarPdf}</span></p>
                        <p>Immunizations: <span> ${immunizationsPdf}</span></p>
                        <p>Mobile App: <span>${mobileAppLink}</span></p>
                    </div>
                    <div class="school-details-4 mx-1">
                        <p>Transportation: <span> ${transportation}${transportationLink}</span></p>
                        <p>Daycare: <span> ${daycare}${daycarePdf}</span></p>
                        <p>Financial: <span> ${financial}${financialPdf}</span></p>
                        <p>Disability: <span> ${disability}${disabilityPdf}</span></p>
                        <p>Language: <span> ${language}${languagePdf}</span></p>
                    </div>
                </div>
            </div>
        `);
        $('#selected_school').on('click', "#findDirection" , e => {
            calculateAndDisplayRoute(`${school.streetAddress}, ${school.zipCode}`);
            $('.grid-item-4').show('slow');
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