;
(function ($) {
    'use strict';
    let $document = $(document),
            $body = $('body'),
            $window = $(window),
            $biolife_slide = $('.biolife-carousel'),
            $scroll_items = $('.biolife-cart-info .minicart-block ul.products'),
            $vertical_menu = $('#header .vertical-category-block:not(.always)'),
            $menu_mobile = $('.clone-main-menu'),
            $sticky_object = $('.biolife-sticky-object'),
            $shop_filter = $('#top-functions-area'),
            $biolife_select = $('select:not(.hidden)'),
            $rating_form = $('.comment-form-rating'),
            $accodition = $('.biolife-accodition'),
            $block_tab = $('.biolife-tab-contain'),
            $biolife_countdown = $('.biolife-countdown:not(.on_testing_mode)'),
            $biolife_popup = $('.biolife-popup'),
            $pre_loader = $('#biof-loading'),
            $btn_scroll_top = $('.btn-scroll-top'),
            $biolife_stretch_the_right_background = $('.biolife-stretch-the-right-background');

    /*Create Mobile Menu*/
    if ($menu_mobile.length) {
        $menu_mobile.biolife_menu_mobile();
    }

    /*Register Quickview Box*/
    if ($('#biolife-quickview-block').length) {
        $document.on('click', '.btn_call_quickview', function (e) {
            e.preventDefault();
            $('body').trigger('open-overlay', ['open-quickview-block']);
            $('#biolife-quickview-block-popup').modal('show');
        })
    }

    /*Register Select Element*/
    if ($biolife_select.length) {
        $biolife_select.niceSelect()
    }

    /*Minicart Scroll handle*/
    if ($scroll_items.length) {
        $scroll_items.niceScroll();
    }

    /*Carousel Handle*/
    if ($biolife_slide.length) {
        $biolife_slide.biolife_init_carousel();
    }

    /*Vertical Menu Handle*/
    if ($vertical_menu.length) {
        $vertical_menu.biolife_vertical_menu();
    }

    /*Toggle shop filter on mobile*/
    if ($shop_filter.length) {
        $shop_filter.on('click', 'a.icon-for-mobile', function (e) {
            e.preventDefault();
            $body.trigger('open-overlay', ['top-refine-opened']);
        });
    }

    /*Header Sticky*/
    if ($sticky_object.length) {
        $sticky_object.biolife_sticky_header();
    }

    /*Tab button*/
    if ($block_tab.length) {
        $block_tab.biolife_tab();
    }

    /*Rating on single product*/
    if ($rating_form.length) {
        $rating_form.biolife_rating_form_handle();
    }

    /*Accodition menu*/
    if ($accodition.length) {
        $accodition.biolife_accodition_handle();
    }

    /*Countdown*/
    if ($biolife_countdown.length) {
        $biolife_countdown.biolife_countdown();
    }

    /*stretch right background*/
    if ($biolife_stretch_the_right_background.length) {
        $biolife_stretch_the_right_background.biolife_stretch_the_right_background();
        window.onresize = function (event) {
            event.preventDefault();
            $biolife_stretch_the_right_background.biolife_stretch_the_right_background();
        };
    }

    /*Popup*/
    if ($biolife_popup.length) {
        $biolife_popup.modal('show');
    }

    /*Scroll to top*/
    if ($btn_scroll_top.length) {
        $window.on('scroll', function () {
            if ($window.scrollTop() >= 800) {
                $btn_scroll_top.addClass('showUp');
            } else {
                $btn_scroll_top.removeClass('showUp');
            }
        });
        $btn_scroll_top.on('click', function () {
            $('html, body').animate({
                scrollTop: 0
            }, 1500);
        });
    }

    /*Events On Document*/
    $document.on('click', '.minicart-item .action .edit', function (e) {
        e.preventDefault();
        let $this = $(this),
                cart_item = $this.closest('.minicart-item'),
                input_field = cart_item.find('.input-qty'),
                curent_val = input_field.val();
        if (!cart_item.hasClass('editing')) {
            cart_item.addClass('editing');
            input_field.removeAttr('disabled').val('');
            input_field.val(curent_val).focus();
        } else {
            cart_item.removeClass('editing');
            input_field.attr('disabled', 'disabled');
        }
    });

    $document.on('click', '.minicart-item .action .remove', function (e) {
        e.preventDefault();
        let _this = $(this),
                minicart_item = _this.closest('li'),
                block_minicart = _this.closest('.cart-inner');
        minicart_item.remove();
        $('body').trigger('update-minicart', [block_minicart]);
    });

//    $document.on('click', '.cart_item .action .remove', function (e) {
//        e.preventDefault();
//        let _this = $(this),
//                cart_item = _this.closest('tr'),
//                block_cart = _this.closest('.shop_table');
//        cart_item.remove();
//        $('body').trigger('update-cart', [block_cart]);
//    });

    $document.on('click', '#overlay', function (e) {
        e.preventDefault();
        let _this = $(this),
                current_class = _this.attr('data-object'),
                class_list = 'open-overlay';
        if (typeof current_class !== "undefined" && current_class !== '') {
            class_list += ' ' + current_class;
            _this.attr('data-object', '');
        }
        $('body').removeClass(class_list);
    });

    $document.on('click', '.mobile-search .btn-close', function (e) {
        e.preventDefault();
        $('body').removeClass('open-overlay open-mobile-search');
    });

    $document.on('click', '.mobile-search .open-searchbox, .dsktp-open-searchbox', function (e) {
        e.preventDefault();
        $body.trigger('open-overlay', ['open-mobile-search']);
    });

    $document.on('click', '.mobile-footer .btn-toggle, .mobile-menu-toggle .btn-toggle', function (e) {
        e.preventDefault();
        let class_name = $(this).attr('data-object');
        if (typeof class_name !== "undefined") {
            $body.trigger('open-overlay', [class_name]);
        }
    });

    $document.on('click', '.biolife-mobile-panels .biolife-close-btn, .biolife-panels-actions-wrap .biolife-close-btn, .btn-close-quickview', function (e) {
        e.preventDefault();
        let class_name = $(this).attr('data-object');
        if (typeof class_name !== 'undefined') {
            $body.trigger('close-overlay', [class_name]);
        }
    });

    $document.on('click', '.biolife-filter .check-list .check-link', function (e) {
        e.preventDefault();
        let this_item = $(this),
                father = this_item.parent(),
                contain = this_item.closest('ul.check-list');
        if (!contain.hasClass('multiple')) {
            father.siblings().removeClass('selected');
        }
        father.toggleClass('selected');
    });

    $document.on('click', '.biolife-filter .color-list .c-link', function (e) {
        e.preventDefault();
        let father = $(this).parent();
        father.siblings().removeClass('selected');
        father.toggleClass('selected');
    });

    $document.on('click', '.qty-input .qty-btn', function (e) {
        e.preventDefault();
        let btn = $(this),
                input = btn.siblings("input[name^='qty']");
        if (input.length) {
            let current_val = parseInt(input.val(), 10),
                    max_val = parseInt(input.data('max_value'), 10),
                    step = parseInt(input.data('step'), 10);
            if (btn.hasClass('btn-up')) {
                current_val += step;
                if (current_val <= max_val) {
                    input.val(current_val);
                }
            } else {
                current_val -= step;
                if (current_val > 0) {
                    input.val(current_val);
                }
            }
        }
    });

    /*Events On Body Target*/
    $body.on('update-minicart', function (el, block_minicart) {
        if (block_minicart.find('ul.products li').length === 0) {
            block_minicart.html('<p class="minicart-empty">No product in your cart</p>');
        }
    });

    $body.on('update-cart', function (el, block_minicart) {
        if (block_minicart.find('.cart_item').length === 0) {
            block_minicart.html('<p class="minicart-empty">No products here</p>');
        }
    });

    $body.on('open-overlay', function (e, classes) {
        let addition_classes = 'open-overlay';
        if (classes !== '') {
            addition_classes += ' ' + classes;
            $('#overlay').attr('data-object', classes);
        }
        $body.addClass(addition_classes);
    });

    $body.on('close-overlay', function (e, object) {
        let classes = 'open-overlay';
        if (object !== '') {
            classes += ' ' + object;
            $('#overlay').attr('data-object', '');
        }
        $body.removeClass(classes);
    });

    /*Create overlay Element*/
    $body.append('<div id="overlay"></div>');

    $.fn.biolife_best_equal_products();

    /*preload handle*/
    $window.on('load', function () {
        if ($pre_loader.length) {
            $pre_loader.fadeOut(800);
            setTimeout(function () {
                $pre_loader.remove();
            }, 3000);
        }
    });
})(jQuery);

function showAlert(userID) {
    if (userID) {
        swal({
            title: "Successfully",
            text: 'The product has been added to cart',
            icon: 'success'
        });
    } else {
        window.location = '/EJBAssignment-war/faces/login.xhtml';
    }
}

$(document).ready(function () {
    $('.desc-tab').html($('.desc-tab').text());
    $('#loadCart\\:btnLoadCart').click();
});

function saveCart() {
    $('#actionForm\\:btnSaveCart').click();
    $('#actionForm\\:btnLogout').click();
}

function removeCart(index) {
    swal({
        title: "Do you want to delete this product?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            $('#listCart\\:cart\\:' + index + '\\:btnRemoveCart').click();
        }
    });
}

function fly(username, btn) {
    if (username) {
        var itemImg = $(btn).parent('.buttons').parent('.slide-down-box').parent('.info').parent('.contain-product').find('.product-thumb').find('a').find('img').eq(0);
        flyToElement($(itemImg), $('.minicart-block'));
    } else {
        window.location = '/BiolifeMinimart-war/faces/login.xhtml';
    }
}

function flydetails(username, btn) {
    if (username) {
        var itemImg = $(btn).parent('.buttons').parent('#productDetails\\:0\\:actionFrom').parent('.action-form').parent('.sumary-product').find('.media').find('ul.slider-for').find('.slick-list').find('.slick-track').find('.slick-current').find('img');
        flyToElement($(itemImg), $('.minicart-block'));
    } else {
        window.location = '/BiolifeMinimart-war/faces/login.xhtml';
    }
}

function plusCart(index) {
    $('#listCart\\:cart\\:' + index + '\\:btnUp').click();
}

function minusCart(index) {
    $('#listCart\\:cart\\:' + index + '\\:btnDown').click();
}

function closeModal() {
    $('.closeModal').click();
}

$("#searchForm\\:inputSearch").on('keyup', function (e) {
    if (e.key === 'Enter' || e.keyCode === 13) {
        $('#searchForm\\:viewAllItems').click();
    } else {
        if (!$('#searchForm\\:inputSearch').val()) {
            $('#searchForm\\:inputSearch').css("border-bottom-left-radius", "24px");
            $('#searchForm\\:inputSearch').css("border-bottom-right-radius", "24px");
            $('#searchForm\\:inputSearch').css("box-shadow", "none");
            $('#searchBox').css("visibility", "hidden");
        } else {
            $('#searchForm\\:inputSearch').css("border-bottom-left-radius", "0");
            $('#searchForm\\:inputSearch').css("border-bottom-right-radius", "0");
            $('#searchForm\\:inputSearch').css("box-shadow", "0 4px 6px 0 rgba(23,23,23,0.5)");
            $('#searchBox').css("box-shadow", "0 4px 6px 0 rgba(23,23,23,0.5)")
            $('#searchBox').css("visibility", "visible");
        }
        $('#searchForm\\:btnSearch').click();
    }
});

var click_in_process = false;
$(function () {
    $("#searchBox li").click(function () {
        $('#searchForm\\:inputSearch').css("border-bottom-left-radius", "0");
        $('#searchForm\\:inputSearch').css("border-bottom-right-radius", "0");
        $('#searchForm\\:inputSearch').css("box-shadow", "0 4px 6px 0 rgba(23,23,23,0.5)");
        $('#searchBox').css("box-shadow", "0 4px 6px 0 rgba(23,23,23,0.5)")
        $('#searchBox').css("visibility", "visible");
    });
    $("#searchForm\\:inputSearch").focus(function () {
        $('#searchForm\\:btnSearch').click();
        if ($('#searchForm\\:inputSearch').val()) {
            $(this).css("border-bottom-left-radius", "0");
            $(this).css("border-bottom-right-radius", "0");
            $('#searchBox').css("visibility", "visible");
            $('#searchForm\\:inputSearch').css("box-shadow", "0 4px 6px 0 rgba(23,23,23,0.5)");
            $('#searchBox').css("box-shadow", "0 4px 6px 0 rgba(23,23,23,0.5)")
        } else {
            $(this).css("border-bottom-left-radius", "24px");
            $(this).css("border-bottom-right-radius", "24px");
            $('#searchForm\\:inputSearch').css("box-shadow", "none");
            $('#searchBox').css("visibility", "hidden");
        }
    });

    $('#searchBox').mousedown(function () {
        click_in_process = true;
    });

    $('#searchBox').mouseup(function () {
        click_in_process = false;
        $('##searchForm\\:inputSearch').focus();
    });

    $("#searchForm\\:inputSearch").blur(function () {
        if (!click_in_process) {
            $(this).css("border-bottom-left-radius", "24px");
            $(this).css("border-bottom-right-radius", "24px");
            $('#searchForm\\:inputSearch').css("box-shadow", "none");
            $('#searchBox').css("visibility", "hidden");
        }
    });
});

function redirectProducts() {
    $('#menuForm\\:productLink').click();
}

function viewAllResult() {
    $('#searchForm\\:viewAllLink').click();
}

function clearCart() {
    swal({
        title: "Do you want to clear the shopping cart?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((willDelete) => {
        if (willDelete) {
            $('#listCart\\:btnEmptyCart').click();
        }
    });
}

function viewOrder() {
    $('#view-order').click();
}

function loadDataTb() {
    table = $('#myorders table').DataTable({

        order: [[0, 'desc']]
//		dom: 'flrtip'
    });

    var html = '<form class="form-inline">'
            + '<label for="order-stt" class="mr-sm-2">Status &nbsp;</label>'
            + '<select name="order-stt">'
            + '<option value="">All</option>'
            + '<option value="Unconfirmed">Unconfirmed</option>'
            + '<option value="Confirmed">Confirmed</option>'
            + '<option value="Delivering">Delivering</option>'
            + '<option value="Delivered">Delivered</option>'
            + '<option value="Received">Received</option>'
            + '<option value="Cancelled">Cancelled</option>'
            + '</select>'
            + '</form>';

    $('#DataTables_Table_0_filter').html(html);
//    $('select').niceSelect();
    $('select[name=order-stt]').on('change', function () {
        var status = $(this).val();
        table.column(3).search(status).draw();
    });
}



$(document).ready(function () {
   // loadDataTb();
    if ($('#countCart').val() == 0) {
        swal({
            title: "Your shopping cart is empty. Please check your cart and try again.",
            icon: "warning",
            button: "OK",
            closeOnClickOutside: false,
        }).then((value) => {
            window.location.href = '/BiolifeMinimart-war/faces/shoppingcart.xhtml';
        });
    } else {
        if ($('#checkReceiver').val() == 0) {
            swal({
                title: "You don't have any Delivery Address. Do you want to create new?",
                icon: "warning",
                buttons: ["Go back", "Yes"],
                closeOnClickOutside: false,
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                    window.location.href = '/BiolifeMinimart-war/faces/setting.xhtml?page=addreceiver';
                } else {
                    window.location.href = '/BiolifeMinimart-war/faces/shoppingcart.xhtml';
                }
            });
        }
    }
    loadDataTb();
    $('#productDetails\\:0\\:changeQuantity\\:resetQuantity').click();
});

$(function () {
    $("#showPassword").click(function () {
        $(this).toggleClass("fa-eye fa-eye-slash");
        var type = $(this).hasClass("fa-eye") ? "text" : "password";
        $("#loginUser\\:password").attr("type", type);
    });
});

//Duy Khanh start

function showImage() {
    var fileSelected = document.getElementById('uploadAvt').files;
    if (fileSelected.length > 0) {
        var fileToLoad = fileSelected[0];
        var fileReader = new FileReader();
        fileReader.onload = function (fileLoaderEvent) {
            var srcData = fileLoaderEvent.target.result;
            var showImage = document.getElementById('displayAvt');
            showImage.src = srcData;
            document.getElementById('displayAvt').innerHTML = showImage.outerHTML;
        }
        fileReader.readAsDataURL(fileToLoad);
    }
}

$(document).ready(function () {

    var urlCurrent = window.location.href;

    $("#personal").show();
    $("#change_password").hide();
    $("#myorder").hide();
    $("#receiver").hide();
    $("#feedback").hide();
    $("#receiver_create").hide();
    
    $("#show_personal").click(function () {
        $("#personal").show();
        $("#change_password").hide();
        $("#myorder").hide();
        $("#receiver").hide();
        $("#feedback").hide();
        $("#receiver_create").hide();
    });

    $("#show_change_pass").click(function () {
        $("#personal").hide();
        $("#change_password").show();
        $("#myorder").hide();
        $("#receiver").hide();
        $("#feedback").hide();
        $("#receiver_create").hide();
    });

    $("#show_order").click(function () {
        $("#personal").hide();
        $("#change_password").hide();
        $("#myorder").show();
        $("#receiver").hide();
        $("#feedback").hide();
        $("#receiver_create").hide();
    });

    $("#show_receiver").click(function () {
        $("#personal").hide();
        $("#change_password").hide();
        $("#myorder").hide();
        $("#receiver").show();
        $("#feedback").hide();
        $("#receiver_create").hide();
    });

    $("#show_feedback").click(function () {
        $("#personal").hide();
        $("#change_password").hide();
        $("#myorder").hide();
        $("#receiver").hide();
        $("#feedback").show();
        $("#feedback_create").hide();
        $("#receiver_create").hide();
    });
    $("#showReceiverCreate").click(function () {
        $("#personal").hide();
        $("#change_password").hide();
        $("#myorder").hide();
        $("#receiver").hide();
        $("#feedback").hide();
        $("#receiver_create").show();
    });
    
    if (urlCurrent.indexOf("addreceiver") !== -1) {
        $("#show_receiver").click();
        $("#showReceiverCreate").click();
    }
    
    if (urlCurrent.indexOf("myorders") !== -1) {
        $("#show_order").click();
    }
})
//Duy Khanh end

function clickRate(num) {
    document.querySelector('.rateValue').value = num;
}


function loadDataReceiver() {
    Employeetable = $('#myreceivers table').DataTable();
}
function loadDataFeedback() {
    table = $('#myfeedback table').DataTable({
        order: [[0, 'desc']]
    });
}
