let turnOn = (String(localStorage.getItem('turnOn')).toLowerCase() === 'true');

$(document).ready(function () {
    var urlCurrent = window.location.href;
    var searchOn = false;
    var maxPageNumber = $('#maxPageNumber').val();
    var currentPage = $('#currentPage').val();
    var currentElementIndex = -1;
    var elements;
    var previousValue;
    var currentValue;
    var elements1;
    var previousValue1;
    var currentValue1;
    var currentElementIndex1 = -1;

    $(document).keydown(function (e) {
        // check keydown
        if (searchOn) {
            switch (e.which) {
                case 8:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Backspace";
                    window.speechSynthesis.speak(msg);
                    break;
                case 9:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Tab";
                    window.speechSynthesis.speak(msg);
                    break;
                case 13:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Enter";
                    window.speechSynthesis.speak(msg);
                    break;
                case 20:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Caps lock";
                    window.speechSynthesis.speak(msg);
                    break;
                case 27:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Esc";
                    window.speechSynthesis.speak(msg);
                    break;
                case 32:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Space";
                    window.speechSynthesis.speak(msg);
                    break;
                case 46:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Delete";
                    window.speechSynthesis.speak(msg);
                    break;
                case 48:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "0";
                    window.speechSynthesis.speak(msg);
                    break;
                case 49:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "1";
                    window.speechSynthesis.speak(msg);
                    break;
                case 50:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "2";
                    window.speechSynthesis.speak(msg);
                    break;
                case 51:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "3";
                    window.speechSynthesis.speak(msg);
                    break;
                case 52:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "4";
                    window.speechSynthesis.speak(msg);
                    break;
                case 53:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "5";
                    window.speechSynthesis.speak(msg);
                    break;
                case 54:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "6";
                    window.speechSynthesis.speak(msg);
                    break;
                case 55:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "7";
                    window.speechSynthesis.speak(msg);
                    break;
                case 56:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "8";
                    window.speechSynthesis.speak(msg);
                    break;
                case 57:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "9";
                    window.speechSynthesis.speak(msg);
                    break;
                case 65:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "A";
                    window.speechSynthesis.speak(msg);
                    break;
                case 66:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "B";
                    window.speechSynthesis.speak(msg);
                    break;
                case 67:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "C";
                    window.speechSynthesis.speak(msg);
                    break;
                case 68:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "D";
                    window.speechSynthesis.speak(msg);
                    break;
                case 69:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "E";
                    window.speechSynthesis.speak(msg);
                    break;
                case 70:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "F";
                    window.speechSynthesis.speak(msg);
                    break;
                case 71:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "G";
                    window.speechSynthesis.speak(msg);
                    break;
                case 72:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "H";
                    window.speechSynthesis.speak(msg);
                    break;
                case 73:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "I";
                    window.speechSynthesis.speak(msg);
                    break;
                case 74:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "J";
                    window.speechSynthesis.speak(msg);
                    break;
                case 75:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "K";
                    window.speechSynthesis.speak(msg);
                    break;
                case 76:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "L";
                    window.speechSynthesis.speak(msg);
                    break;
                case 77:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "M";
                    window.speechSynthesis.speak(msg);
                    break;
                case 78:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "N";
                    window.speechSynthesis.speak(msg);
                    break;
                case 79:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "O";
                    window.speechSynthesis.speak(msg);
                    break;
                case 80:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "P";
                    window.speechSynthesis.speak(msg);
                    break;
                case 81:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Q";
                    window.speechSynthesis.speak(msg);
                    break;
                case 82:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "R";
                    window.speechSynthesis.speak(msg);
                    break;
                case 83:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "S";
                    window.speechSynthesis.speak(msg);
                    break;
                case 84:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "T";
                    window.speechSynthesis.speak(msg);
                    break;
                case 85:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "U";
                    window.speechSynthesis.speak(msg);
                    break;
                case 86:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "V";
                    window.speechSynthesis.speak(msg);
                    break;
                case 87:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "W";
                    window.speechSynthesis.speak(msg);
                    break;
                case 88:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "X";
                    window.speechSynthesis.speak(msg);
                    break;
                case 89:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Y";
                    window.speechSynthesis.speak(msg);
                    break;
                case 90:
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Z";
                    window.speechSynthesis.speak(msg);
                    break;
            }
        }

        if (e.which === 77 && e.ctrlKey) { //ctrl + m, turn off voice
            e.preventDefault();
            window.speechSynthesis.cancel();
            turnOn = false;
            localStorage.setItem('turnOn', turnOn);
        } else if (e.which === 79 && e.ctrlKey) { //ctrl + o, turn on voice
            e.preventDefault();
            turnOn = true;
            localStorage.setItem('turnOn', turnOn);
        }

        if (turnOn) {
            if (e.which == 80 && e.altKey) { // alt + p, redirect to products
                e.preventDefault();
                window.speechSynthesis.cancel();
                $('#btnProduct').click();
            } else if (e.which == 66 && e.altKey) { // alt + b, redirect to shoppingcart
                e.preventDefault();
                window.speechSynthesis.cancel();
                $('#formShoppingcart\\:btnShoppingcart').click();
            } else if (e.which == 67 && e.altKey) { // alt + c, redirect to checkout
                e.preventDefault();
                window.speechSynthesis.cancel();
                $('#formCheckout\\:btnCheckout').click();
            } else if (e.which == 72 && e.altKey) { // alt + h, redirect to home
                e.preventDefault();
                window.speechSynthesis.cancel();
                window.location.href = "/BiolifeMinimart-war/faces/index.xhtml";
            } else if (e.which == 77 && e.altKey) { // alt + m, turn off current voice
                e.preventDefault();
                window.speechSynthesis.cancel();
            } else if (e.which == 83 && e.altKey) { // alt + s, focus search textbox
                e.preventDefault();
                window.speechSynthesis.cancel();
                $('#searchForm\\:inputSearch').focus();
                searchOn = true;
            } else if (e.which == 86 && e.altKey) { // alt + v, click btn microphone
                e.preventDefault();
                window.speechSynthesis.cancel();
                $('#btnSearchVoice').click();
            } else {
                return;
            }
        }
    });

    if (urlCurrent.includes("index")) {
        if (turnOn) {
            window.speechSynthesis.cancel();
            var msg = new SpeechSynthesisUtterance();
            msg.text = "Welcome to Biolife Minimart website. This is a feature for blind and visually impaired people. \n\
                        Press Ctrl and M, to turn it off. Ctrl and O, to turn it on.\n\
                        Function keys. Alt and M, to mute the speaking voice. Alt and P, to go to Products page. \n\
                        Alt and B, to go to Shopping Cart page. Alt and C, to go to Checkout page.\n\
                        Alt and S, to search by keyword. Alt and V, to search by voice. Alt and H, to listen again.";
            window.speechSynthesis.speak(msg);
        }
    } else if (urlCurrent.includes("products")) {
        elements = $('.controlElement');
        previousValue = elements[currentElementIndex];
        if (turnOn) {
            window.speechSynthesis.cancel();
            var msg = new SpeechSynthesisUtterance();
            msg.text = "Welcome to the Products page. Use the left and right arrows to hear information about each product. \n\
                        Alt and D, to hear the description of the product. Alt and A, to add to cart. \n\
                        Alt and H, to return to the Home page. Alt and P, to listen again.";
            window.speechSynthesis.speak(msg);
        }
        $(document).keydown(function (e) {
            if (turnOn) {
                if (e.which == 37) { // left arrow
                    e.preventDefault();
                    currentElementIndex--;
                    if (currentElementIndex < 0) {
                        var page = parseInt(currentPage) - 1;
                        if (page < 1) {
                            page = maxPageNumber;
                        }
                        var urlNext = "/BiolifeMinimart-war/faces/products.xhtml?page=" + page;
                        window.location.href = urlNext;

                    }
                } else if (e.which == 39) { // right arrow
                    e.preventDefault();
                    currentElementIndex++;
                    if (currentElementIndex >= elements.length) {
                        var page = parseInt(currentPage) + 1;
                        if (page > maxPageNumber) {
                            page = 1;
                        }
                        var urlNext = "/BiolifeMinimart-war/faces/products.xhtml?page=" + page;
                        window.location.href = urlNext;
                    }
                } else if (e.which == 65 && e.altKey) { // alt + a, add to cart
                    e.preventDefault();
                    $('#prodform\\:prodList\\:' + currentValue.id.substring(10) + '\\:addCart').click();
                } else if (e.which == 68 && e.altKey) { // alt + d, description
                    e.preventDefault();
                    $('#prodform\\:prodList\\:' + currentValue.id.substring(10) + '\\:infoBtnDes').click();
                }
            }
            currentValue = elements[currentElementIndex];

            if (previousValue != currentValue) {
                $('#prodform\\:prodList\\:' + currentValue.id.substring(10) + '\\:infoBtn').click();
                previousValue = currentValue;
            }
        });
    } else if (urlCurrent.includes("shoppingcart")) {
        var validDelete = false;
        var validEmpty = false;
        elements = $('.elementShopping');
        previousValue = elements[currentElementIndex];
        var size = elements.length;
        var product;
        if (size == 0) {
            product = "You have no product in your cart";
        } else if (size == 1) {
            product = "You have " + size + " product in your cart";
        } else if (size > 1) {
            product = "You have " + size + " products in your cart";
        }
        if (turnOn) {
            if (elements.length == 0) {
                window.speechSynthesis.cancel();
                var msg = new SpeechSynthesisUtterance();
                msg.text = "Welcome to shopping cart. " + product + ". Alt and P, to go to Products page.\n\
                            Alt and H, to return to the Home page. Alt and B, to listen again.";
                window.speechSynthesis.speak(msg);
            } else {
                window.speechSynthesis.cancel();
                var msg = new SpeechSynthesisUtterance();
                msg.text = "Welcome to shopping cart. " + product +
                        ". Use the left and right arrows to hear information about each product.\n\
                            Use the up and down arrows to increase or decrease the quantity of each product. \n\
                            Alt and T, to hear the total amount. Alt and D, to remove the product from the cart. \n\
                            Alt and A, to remove all products in the cart. Alt and C, to go to Checkout page. \n\
                            Alt and H, to return to the Home page. Alt and B, to listen again.";
                window.speechSynthesis.speak(msg);
            }
        }
        $(document).keydown(function (e) {
            if (turnOn) {
                if (e.which == 37) { // left arrow
                    e.preventDefault();
                    currentElementIndex--;
                    if (size == 0) {
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "You have no product in yout cart";
                        window.speechSynthesis.speak(msg);
                    } else if (currentElementIndex < 0) {
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "You are standing at first product";
                        window.speechSynthesis.speak(msg);
                        currentElementIndex = -1;
                    }
                    currentValue = elements[currentElementIndex];
                    $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:shoppingBtn').click();
                    previousValue = currentValue;
                } else if (e.which == 39) { // right arrow
                    e.preventDefault();
                    currentElementIndex++;
                    if (size == 0) {
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "You have no product in yout cart";
                        window.speechSynthesis.speak(msg);
                    } else if (currentElementIndex >= size) {
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "You are standing at last product";
                        window.speechSynthesis.speak(msg);
                        currentElementIndex = size;
                    }
                    currentValue = elements[currentElementIndex];
                    $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:shoppingBtn').click();
                    previousValue = currentValue;
                } else if (e.which == 38) { // up arrow
                    e.preventDefault();
                    $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:btnUp').click();
                    var maxQty = $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:maxQuantity').val();
                    var q = parseInt($('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:quantity').val()) + 1;
                    if (q > maxQty) {
                        q = maxQty;
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "The max quantity of this product is " + maxQty;
                        window.speechSynthesis.speak(msg);
                    } else {
                        getQuantity(q);
                    }
                } else if (e.which == 40) { // down arrow
                    e.preventDefault();
                    $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:btnDown').click();
                    var q = $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:quantity').val() - 1;
                    if (q == 0) {
                        q = 1;
                    }
                    getQuantity(q);
                } else if (e.which == 84 && e.altKey) { // alt + t
                    e.preventDefault();
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Total is " + $('#total').text();
                    window.speechSynthesis.speak(msg);
                } else if (e.which == 68 && e.altKey) { // alt + d
                    e.preventDefault();
                    $('#listCart\\:cart\\:' + currentValue.id.substring(8) + '\\:btnRemove').click();
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Do you want to delete this product? Click Y to delete or N to cancel.";
                    window.speechSynthesis.speak(msg);
                    validDelete = true;
                } else if (e.which == 65 && e.altKey) { // alt + a
                    e.preventDefault();
                    $('#listCart\\:btnEmpty').click();
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Do you want to clear the shopping cart? Click Y to clear or N to cancel.";
                    window.speechSynthesis.speak(msg);
                    validEmpty = true;
                } else if (e.which == 89) { // y
                    e.preventDefault();
                    if (validDelete) {
                        $('.swal-button--confirm').click();
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "The product is deleted";
                        window.speechSynthesis.speak(msg);
                        validDelete = false;
                        currentElementIndex--;
                        size--;
                    } else if (validEmpty) {
                        $('.swal-button--confirm').click();
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "Your shopping cart is empty now";
                        window.speechSynthesis.speak(msg);
                        validEmpty = false;
                        currentElementIndex = -1;
                        size = 0;
                    }

                } else if (e.which == 78) { // n
                    e.preventDefault();
                    if (validDelete) {
                        $('.swal-button--cancel').click();
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "The action is canceled";
                        window.speechSynthesis.speak(msg);
                        validDelete = false;
                    } else if (validEmpty) {
                        $('.swal-button--cancel').click();
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "The action is canceled";
                        window.speechSynthesis.speak(msg);
                        validEmpty = false;
                    }
                }
            }
        });
    } else if (urlCurrent.includes("checkout")) {
        elements = $('.elementCheckout');
        previousValue = elements[currentElementIndex];
        var validChange = false;
        var validCountCart = false;
        elements1 = $('.ui-datatable-selectable');
        previousValue1 = elements1[currentElementIndex1];
        var size = elements1.length;
        var product;
        if (elements.length == 1) {
            product = "You have " + elements.length + " product in your cart";
        } else if (elements.length > 1) {
            product = "You have " + elements.length + " products in your cart";
        }
        if (turnOn) {
            if (elements.length == 0) {
                window.speechSynthesis.cancel();
                var msg = new SpeechSynthesisUtterance();
                msg.text = "Your shopping cart is empty. Please check your cart and try again. Click enter to go to the shopping cart";
                window.speechSynthesis.speak(msg);
                validCountCart = true;
            } else {
                window.speechSynthesis.cancel();
                var msg = new SpeechSynthesisUtterance();
                msg.text = "Welcome to the Checkout page. " + product + ". \n\
                            Use the left and right arrows to hear information about each product.\n\
                            Alt and T, to hear the total amount. Alt and A, to hear delivery information. \n\
                            Alt and D, to change the delivery address. Alt and O, to order. \n\
                            Alt and N, to enter notes for the order.\n\
                            Alt and H, to return to the Home page. Alt and C, to listen again.";
                window.speechSynthesis.speak(msg);
            }
        }
        $(document).keydown(function (e) {
            if (turnOn) {
                if (e.which == 37) { // left arrow
                    e.preventDefault();
                    currentElementIndex--;
                    if (currentElementIndex < 0) {
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "You are standing at first product.";
                        window.speechSynthesis.speak(msg);
                        currentElementIndex = -1;
                    }

                    currentValue = elements[currentElementIndex];
                    console.log('Giá trị của phần tử đã thay đổi thành: ' + currentValue);
                    $('#form\\:orderList\\:' + currentValue.id.substring(8) + '\\:checkoutBtn').click();
                    previousValue = currentValue;
                } else if (e.which == 39) { // right arrow
                    e.preventDefault();
                    currentElementIndex++;
                    if (currentElementIndex >= elements.length) {
                        window.speechSynthesis.cancel();
                        var msg = new SpeechSynthesisUtterance();
                        msg.text = "You are standing at last product.";
                        window.speechSynthesis.speak(msg);
                        currentElementIndex = elements.length;
                    }

                    currentValue = elements[currentElementIndex];
                    console.log('Giá trị của phần tử đã thay đổi thành: ' + currentValue);
                    $('#form\\:orderList\\:' + currentValue.id.substring(8) + '\\:checkoutBtn').click();
                    previousValue = currentValue;
                } else if (e.which == 84 && e.altKey) { // alt + t, total price
                    e.preventDefault();
                    $('#form\\:totalBtn').click();
                } else if (e.which == 65 && e.altKey) { // alt + a, delivery address
                    e.preventDefault();
                    $('#form\\:addressBtn').click();
                } else if (e.which == 79 && e.altKey) { // alt + o, place order
                    e.preventDefault();
                    $('#form\\:checkout').click();
                } else if (e.which == 68 && e.altKey) { // alt + d, click change deliery address
                    e.preventDefault();
                    searchOn = false;
                    window.speechSynthesis.cancel();
                    var msg = new SpeechSynthesisUtterance();
                    msg.text = "Click up and down to select delivery address. Then click enter to change your delivery address";
                    window.speechSynthesis.speak(msg);
                    $('#change-address').click();
                    validChange = true;
                } else if (e.which == 78 && e.altKey) { // alt + n, focus note textbox
                    e.preventDefault();
                    $('#form\\:orderNote').focus();
                    searchOn = true;
                    window.speechSynthesis.cancel();
                } else if (e.which == 38) { // up arrow
                    if (validChange) {
                        e.preventDefault();
                        currentElementIndex1--;
                        if (currentElementIndex1 < 0) {
                            window.speechSynthesis.cancel();
                            var msg = new SpeechSynthesisUtterance();
                            msg.text = "You are standing at first delivery address.";
                            window.speechSynthesis.speak(msg);
                            currentElementIndex1 = -1;
                        }
                        currentValue1 = elements1[currentElementIndex1];
                        $('#form\\:addressList\\:' + currentValue1.getAttribute("data-ri") + '\\:addressBtn').parent('td').click();
                        $('#form\\:addressList\\:' + currentValue1.getAttribute("data-ri") + '\\:addressBtn').click();
                    }
                } else if (e.which == 40) { // down arrow
                    if (validChange) {
                        e.preventDefault();
                        currentElementIndex1++;
                        if (currentElementIndex1 >= size) {
                            window.speechSynthesis.cancel();
                            var msg = new SpeechSynthesisUtterance();
                            msg.text = "You are standing at last delivery address.";
                            window.speechSynthesis.speak(msg);
                            currentElementIndex1 = size;
                        }
                        currentValue1 = elements1[currentElementIndex1];
                        $('#form\\:addressList\\:' + currentValue1.getAttribute("data-ri") + '\\:addressBtn').parent('td').click();
                        $('#form\\:addressList\\:' + currentValue1.getAttribute("data-ri") + '\\:addressBtn').click();
                    }
                } else if (e.which == 13) { // enter
                    e.preventDefault();
                    if (validChange) {
                        $('#form\\:addressList\\:okBtn').click();
                        window.speechSynthesis.cancel();
                        validChange = false;
                    } else if (validCountCart) {
                        $('.swal-button--confirm').click();
                        validCountCart = false;
                    }
                }
            }
        });
    } else if (urlCurrent.includes("thankyou")) {
        if (turnOn) {
            window.speechSynthesis.cancel();
            var msg = new SpeechSynthesisUtterance();
            msg.text = "You have placed an order successfully. Alt and P, to return to the Products page. \n\
                        Alt and I, to issue invoices. Alt and H, to return to the Home page.";
            window.speechSynthesis.speak(msg);
        }
        $(document).keydown(function (e) {
            if (turnOn) {
                if (e.which == 73 && e.altKey) { // alt + i, invoice
                    e.preventDefault();
                    $('#invoiceForm\\:invoice').click();
                }
            }
        });
    }

});

function getProductInformation(name, qty, price) {
    window.speechSynthesis.cancel();
    var msg = new SpeechSynthesisUtterance();
    msg.text = "The product's name is " + name + ". Quantity is " + qty + ". Price is " + price + ". ";
    window.speechSynthesis.speak(msg);

}

function getProductDescription(id) {
    $('#description' + id).html($('#description' + id).text());
    window.speechSynthesis.cancel();
    const element = document.getElementById("description" + id);
    const nodeList = element.childNodes;
    var msg = new SpeechSynthesisUtterance();
    msg.text = "Description is ";
    if (i < nodeList.length == 0) {
        msg.text += $('#description' + id).text();
    } else {
        for (let i = 0; i < nodeList.length; i++) {
            if (nodeList[i].nodeName != "#text") {
                msg.text += nodeList[i].innerText + ".";
            }
        }
    }
    window.speechSynthesis.speak(msg);
}

function getShoppingCart(name, price, qty, total) {
    window.speechSynthesis.cancel();
    var msg = new SpeechSynthesisUtterance();
    msg.text = "The product's name is " + name + ". Price is " + price + ". Quantity is " + qty + ". Total is " + total + " .";
    window.speechSynthesis.speak(msg);
}

function getQuantity(quantity) {
    window.speechSynthesis.cancel();
    var msg = new SpeechSynthesisUtterance();
    msg.text = "Quantity is " + quantity;
    window.speechSynthesis.speak(msg);
}

function getCheckout(name, price, qty) {
    window.speechSynthesis.cancel();
    var msg = new SpeechSynthesisUtterance();
    msg.text = "The product's name is " + name + ". Unit Price is " + price + ". Quantity is " + qty;
    window.speechSynthesis.speak(msg);
}

function getTotal(price) {
    window.speechSynthesis.cancel();
    var msg = new SpeechSynthesisUtterance();
    msg.text = "Total is " + price;
    window.speechSynthesis.speak(msg);
}

function getAddress(name, phone, address) {
    window.speechSynthesis.cancel();
    var msg = new SpeechSynthesisUtterance();
    msg.text = "Receiver's name is " + name + ". Receiver's phone number is " + phone + ". Receiver's address is " + address;
    window.speechSynthesis.speak(msg);
}

