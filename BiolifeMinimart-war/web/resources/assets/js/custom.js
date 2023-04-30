/**
 Core script to handle the entire theme and core functions
 **/

var Acara = function () {
    /* Search Bar ============ */
    var screenWidth = $(window).width();

    var handleSelectPicker = function () {
        if (jQuery('.default-select').length > 0) {
            jQuery('.default-select').selectpicker();
        }
    }

    var handleTheme = function () {
        $('#preloader').fadeOut(500);
        $('#main-wrapper').addClass('show');
    }

    var handleMetisMenu = function () {
        if (jQuery('#menu').length > 0) {
            $("#menu").metisMenu();
        }
        jQuery('.metismenu > .mm-active ').each(function () {
            if (!jQuery(this).children('ul').length > 0)
            {
                jQuery(this).addClass('active-no-child');
            }
        });
    }

    var handleAllChecked = function () {
        $("#checkAll").on('change', function () {
            $("td input:checkbox, .email-list .custom-checkbox input:checkbox").prop('checked', $(this).prop("checked"));
        });
    }

    var handleNavigation = function () {
        $(".nav-control").on('click', function () {

            $('#main-wrapper').toggleClass("menu-toggle");

            $(".hamburger").toggleClass("is-active");
        });
    }

    var handleCurrentActive = function () {
        for (var nk = window.location,
                o = $("ul#menu a").filter(function () {

            return this.href == nk;

        })
                .addClass("mm-active")
                .parent()
                .addClass("mm-active"); ; )
        {

            if (!o.is("li"))
                break;

            o = o.parent()
                    .addClass("mm-show")
                    .parent()
                    .addClass("mm-active");
        }
    }

    var handleCustomFileInput = function () {
        $(".custom-file-input").on("change", function () {
            var fileName = $(this).val().split("\\").pop();
            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
        });
    }

    var handleMiniSidebar = function () {
        $("ul#menu>li").on('click', function () {
            const sidebarStyle = $('body').attr('data-sidebar-style');
            if (sidebarStyle === 'mini') {
                console.log($(this).find('ul'))
                $(this).find('ul').stop()
            }
        })
    }

    var handleMinHeight = function () {
        var win_h = window.outerHeight;
        var win_h = window.outerHeight;
        if (win_h > 0 ? win_h : screen.height) {
            $(".content-body").css("min-height", (win_h + 60) + "px");
        }
        ;
    }

    var handleDataAction = function () {
        $('a[data-action="collapse"]').on("click", function (i) {
            i.preventDefault(),
                    $(this).closest(".card").find('[data-action="collapse"] i').toggleClass("mdi-arrow-down mdi-arrow-up"),
                    $(this).closest(".card").children(".card-body").collapse("toggle");
        });

        $('a[data-action="expand"]').on("click", function (i) {
            i.preventDefault(),
                    $(this).closest(".card").find('[data-action="expand"] i').toggleClass("icon-size-actual icon-size-fullscreen"),
                    $(this).closest(".card").toggleClass("card-fullscreen");
        });



        $('[data-action="close"]').on("click", function () {
            $(this).closest(".card").removeClass().slideUp("fast");
        });

        $('[data-action="reload"]').on("click", function () {
            var e = $(this);
            e.parents(".card").addClass("card-load"),
                    e.parents(".card").append('<div class="card-loader"><i class=" ti-reload rotate-refresh"></div>'),
                    setTimeout(function () {
                        e.parents(".card").children(".card-loader").remove(),
                                e.parents(".card").removeClass("card-load")
                    }, 2000)
        });
    }

    var handleHeaderHight = function () {
        const headerHight = $('.header').innerHeight();
        $(window).scroll(function () {
            if ($('body').attr('data-layout') === "horizontal" && $('body').attr('data-header-position') === "static" && $('body').attr('data-sidebar-position') === "fixed")
                $(this.window).scrollTop() >= headerHight ? $('.deznav').addClass('fixed') : $('.deznav').removeClass('fixed')
        });
    }

    var handleDzScroll = function () {
        jQuery('.dz-scroll').each(function () {

            var scroolWidgetId = jQuery(this).attr('id');
            const ps = new PerfectScrollbar('#' + scroolWidgetId, {
                wheelSpeed: 2,
                wheelPropagation: true,
                minScrollbarLength: 20
            });
        })
    }

    var handleMenuTabs = function () {
        if (screenWidth <= 991) {
            jQuery('.menu-tabs .nav-link').on('click', function () {
                if (jQuery(this).hasClass('open'))
                {
                    jQuery(this).removeClass('open');
                    jQuery('.fixed-content-box').removeClass('active');
                    jQuery('.hamburger').show();
                } else {
                    jQuery('.menu-tabs .nav-link').removeClass('open');
                    jQuery(this).addClass('open');
                    jQuery('.fixed-content-box').addClass('active');
                    jQuery('.hamburger').hide();
                }
                //jQuery('.fixed-content-box').toggleClass('active');
            });
            jQuery('.close-fixed-content').on('click', function () {
                jQuery('.fixed-content-box').removeClass('active');
                jQuery('.hamburger').removeClass('is-active');
                jQuery('#main-wrapper').removeClass('menu-toggle');
                jQuery('.hamburger').show();
            });
        }
    }

    var handleChatbox = function () {
        jQuery('.bell-link').on('click', function () {
            jQuery('.chatbox').addClass('active');
        });
        jQuery('.chatbox-close').on('click', function () {
            jQuery('.chatbox').removeClass('active');
        });
    }

    var handleBtnNumber = function () {
        $('.btn-number').on('click', function (e) {
            e.preventDefault();

            fieldName = $(this).attr('data-field');
            type = $(this).attr('data-type');
            var input = $("input[name='" + fieldName + "']");
            var currentVal = parseInt(input.val());
            if (!isNaN(currentVal)) {
                if (type == 'minus')
                    input.val(currentVal - 1);
                else if (type == 'plus')
                    input.val(currentVal + 1);
            } else {
                input.val(0);
            }
        });
    }

    var handleDzChatUser = function () {
        jQuery('.dz-chat-user-box .dz-chat-user').on('click', function () {
            jQuery('.dz-chat-user-box').addClass('d-none');
            jQuery('.dz-chat-history-box').removeClass('d-none');
        });

        jQuery('.dz-chat-history-back').on('click', function () {
            jQuery('.dz-chat-user-box').removeClass('d-none');
            jQuery('.dz-chat-history-box').addClass('d-none');
        });

        jQuery('.dz-fullscreen').on('click', function () {
            jQuery('.dz-fullscreen').toggleClass('active');
        });
    }

    var handleDzLoadMore = function () {
        $(".dz-load-more").on('click', function (e)
        {
            e.preventDefault();	//STOP default action
            $(this).append(' <i class="fa fa-refresh"></i>');

            var dzLoadMoreUrl = $(this).attr('rel');
            var dzLoadMoreId = $(this).attr('id');

            $.ajax({
                method: "POST",
                url: dzLoadMoreUrl,
                dataType: 'html',
                success: function (data) {
                    $("#" + dzLoadMoreId + "Content").append(data);
                    $('.dz-load-more i').remove();
                }
            })
        });
    }

    var handleDzFullScreen = function () {
        jQuery('.dz-fullscreen').on('click', function (e) {
            if (document.fullscreenElement || document.webkitFullscreenElement || document.mozFullScreenElement || document.msFullscreenElement) {
                /* Enter fullscreen */
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } else if (document.msExitFullscreen) {
                    document.msExitFullscreen(); /* IE/Edge */
                } else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen(); /* Firefox */
                } else if (document.webkitExitFullscreen) {
                    document.webkitExitFullscreen(); /* Chrome, Safari & Opera */
                }
            } else { /* exit fullscreen */
                if (document.documentElement.requestFullscreen) {
                    document.documentElement.requestFullscreen();
                } else if (document.documentElement.webkitRequestFullscreen) {
                    document.documentElement.webkitRequestFullscreen();
                } else if (document.documentElement.mozRequestFullScreen) {
                    document.documentElement.mozRequestFullScreen();
                } else if (document.documentElement.msRequestFullscreen) {
                    document.documentElement.msRequestFullscreen();
                }
            }
        });
    }

    var handlePerfectScrollbar = function () {
        if (jQuery('.deznav-scroll').length > 0)
        {
            const qs = new PerfectScrollbar('.deznav-scroll');
        }
    }

    var heartBlast = function () {
        $(".heart").on("click", function () {
            $(this).toggleClass("heart-blast");
        });
    }

    var handleshowPass = function () {
        jQuery('.show-pass').on('click', function () {
            jQuery(this).toggleClass('active');
            if (jQuery('#dz-password').attr('type') == 'password') {
                jQuery('#dz-password').attr('type', 'text');
            } else if (jQuery('#dz-password').attr('type') == 'text') {
                jQuery('#dz-password').attr('type', 'password');
            }
        });
    }

    /* Function ============ */
    return {
        init: function () {
            handleSelectPicker();
            handleTheme();
            handleMetisMenu();
            handleAllChecked();
            handleNavigation();
            handleCurrentActive();
            handleCustomFileInput();
            handleMiniSidebar();
            handleMinHeight();
            handleDataAction();
            handleHeaderHight();
            handleDzScroll();
            handleMenuTabs();
            handleChatbox();
            handleBtnNumber();
            handleDzChatUser();
            handleDzLoadMore();
            handleDzFullScreen();
            handlePerfectScrollbar();
            heartBlast();
            handleshowPass();

        },

        load: function () {
            handleSelectPicker();
            handleTheme();
        },

        resize: function () {


        }
    }

}();

/* Document.ready Start */
jQuery(document).ready(function () {
    $('[data-toggle="popover"]').popover();
    'use strict';
    Acara.init();

});
/* Document.ready END */

/* Window Load START */
jQuery(window).on('load', function () {
    'use strict';
    Acara.load();

});
/*  Window Load END */
/* Window Resize START */
jQuery(window).on('resize', function () {
    'use strict';
    Acara.resize();
});
/*  Window Resize END */

function taghtml(id) {
    $('.desc-tab' + id).html($('.desc-tab' + id).text());
}

function deleteData(id) {
    swal({
        title: "Are you sure?",
        text: "If this item has one or more foreign keys, its status will be disabled. Otherwise, it will be deleted.",
        icon: "warning",
        buttons: ["Cancel", "Delete"],
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            $('#action' + id + '\\:btnDelete' + id + '').click();
        }
    });
}

function demoPrint() {
    html2canvas(document.getElementById("selectStatistics:barChart_canvas"), {
        allowTaint: true, useCORS: true,
        onclone: function (clonedDoc) {

            // I made the div hidden and here I am changing it to visible
            clonedDoc.getElementById('selectStatistics:barChart_canvas').style.display = 'block';
        }
    }).then(function (canvas) {
        var target = new Image();
        target.src = canvas.toDataURL();
        var url = target.src;
        document.getElementById('selectStatistics:strImage').value = url.substring(22);
        $('#selectStatistics\\:btnExport').click();
    });
}

Date.prototype.yyyymmdd = function () {
    var mm = this.getMonth() + 1; // getMonth() is zero-based
    var dd = this.getDate();

    return [(mm > 9 ? '' : '0') + mm,
        (dd > 9 ? '' : '0') + dd,
        this.getFullYear()
    ].join('/');
};

jQuery(document).ready(function () {
    var dateStart;
    dateStart = new Date();
    var firstDateofMonth = [((dateStart.getMonth() + 1) > 9 ? '' : '0') + (dateStart.getMonth() + 1),
        '01',
        dateStart.getFullYear()
    ].join('/');

    $('.promotionDatepicker').daterangepicker({
        format: 'MM/DD/YYYY',
        minDate: dateStart.yyyymmdd(),
        showButtonPanel: true,
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-danger',
        cancelClass: 'btn-inverse'
    });

    $('.revenueDatepicker').daterangepicker({
        format: 'MM/DD/YYYY',
        showButtonPanel: true,
        buttonClasses: ['btn', 'btn-sm'],
        applyClass: 'btn-danger',
        cancelClass: 'btn-inverse',
        startDate: firstDateofMonth,
        endDate: dateStart.yyyymmdd(),
        dateLimit: {
            days: 30
        }
    });
    
    $('#selectStatistics\\:btnChangeDate').click();
});

$('#promotionInsertForm\\:promotionDate').on("keydown", function () {
    $('#promotionInsertForm\\:promotionDate').blur();
});

function deletePromotion(id, index) {
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this!",
        icon: "warning",
        buttons: ["Cancel", "Delete"],
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            $('#promotionForm\\:listPromotion\\:' + index + '\\:btnDeletePromotion').click();
        }
    });
}

function deletePromotionDetails(id, index) {
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this!",
        icon: "warning",
        buttons: ["Cancel", "Delete"],
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            $('#promotionDetailsForm\\:listPromotionDetails\\:' + index + '\\:btnDeletePromotionDetials').click();
        }
    });
}

jQuery(document).ready(function () {
    $('#promotionInsertForm\\:productList_paginator_top').css('display', 'none');
    $('#promotionInsertForm\\:productList_paginator_bottom').css('display', 'none');
});



function closeModalProduct() {
    $('#promotionInsertForm\\:productString').val($('#promotionInsertForm\\:productList_selection').val());
    $('#promotionInsertForm\\:btnClose').click();
}

function close() {
    $('.closeModal').click();
}

document.querySelector("#files").addEventListener("change", (e) => {
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        const files = e.target.files;
        const output = document.querySelector("#result");
        output.innerHTML = "";
        for (let i = 0; i < files.length; i++) {
            if (!files[i].type.match("image"))
                continue;
            const picReader = new FileReader();
            picReader.addEventListener("load", function (event) {
                const picFile = event.target;
                const div = document.createElement("div");
                div.innerHTML = `<img class="thumbnail" width:150px style="float:left;" src="${picFile.result}"/>`;
                div.style.width = "600x";
                output.appendChild(div);
            });

            picReader.readAsDataURL(files[i]);
        }
    } else {
        alert("Your browser does not support File API");
    }
});
