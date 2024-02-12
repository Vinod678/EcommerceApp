document.addEventListener("DOMContentLoaded", function() {
    // Function to retrieve query parameters from the URL
    function getQueryParam(name) {
        const urlSearchParams = new URLSearchParams(window.location.search);
        return urlSearchParams.get(name);
    }

    // Retrieve product ID and product name from query parameters
    const productId = getQueryParam('productId');
    const productName = decodeURIComponent(getQueryParam('productName'));

    // Retrieve quantity and total cost from session storage
    const quantity = sessionStorage.getItem(`quantity_${productId}`);
    const totalCost = sessionStorage.getItem(`totalCost_${productId}`);

    // Display product name, quantity, and total cost
    document.getElementById('quantity_productId').textContent = quantity;
    document.getElementById('totalCost_productId').textContent = totalCost;

    // Display shipping information
    const userEmail = localStorage.getItem('userEmail');
    const apiUrl = `http://localhost:8080/user-profile/${userEmail}`;
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('userEmail').textContent = data.userEmail;
            document.getElementById('userName').textContent = data.userName;
            document.getElementById('userPhoneNumber').textContent = data.userPhoneNumber;
            document.getElementById('userAddress').textContent = data.userAddress;
        })
        .catch(error => {
            console.error('Error fetching shipping information:', error);
        });

    // Button to go back and make changes to the order
    const editOrderButton = document.getElementById('editOrderButton');
    editOrderButton.addEventListener('click', function() {
        // Redirect to the order item page with the product ID
        window.location.href = `http://localhost:63342/EcommerceApp/static/orderItem.html?productId=${productId}`;
    });

    // Button to proceed to the final step of placing the order
    const placeOrderButton = document.getElementById('placeOrderButton');
    placeOrderButton.addEventListener('click', function() {
        // Construct the request body
        const orderData = {
            userId: parseInt(localStorage.getItem('userId')),
            productId: parseInt(productId),
            quantity: parseInt(quantity),
            totalPrice: parseFloat(totalCost),
            orderDate: new Date().toISOString(),
            status: "Pending",
            shippingAddress: document.getElementById('userAddress').textContent.trim(),
            paymentMethod: getSelectedPaymentMethod() // Assuming you have a function to retrieve the selected payment method
        };

        // Make a POST request to create the order
        fetch('http://localhost:8080/orders/createOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create order');
            }
            // Handle successful order creation (e.g., redirect to order confirmation page)
            window.location.href = `http://localhost:63342/EcommerceApp/static/orderConfirmation.html?productId=${productId}`;
        })
        .catch(error => {
            console.error('Error placing order:', error);
            // Handle errors (e.g., display error message to user)
            alert('Failed to place order. Please try again later.');
        });
    });


// Select Payment Method
    function getSelectedPaymentMethod() {
        const paymentOptions = document.getElementsByName('paymentOption');
        let selectedMethod = '';

        // Loop through all radio buttons
        paymentOptions.forEach(option => {
            if (option.checked) {
                // If a radio button is checked, set selectedMethod to its value
                selectedMethod = option.value;
            }
        });

        return selectedMethod;
    }





    // Function to check if the user is logged in , If not loggedIn it will redirect to homepage
    function isUserLoggedIn() {
        // Retrieve the login status from localStorage
        const loggedIn = localStorage.getItem('loggedIn');
        // Check if the user is logged in based on the stored value
        return loggedIn === 'true';
    }

    // Check if the user is logged in when the page loads
    if (!isUserLoggedIn()) {
        // Redirect to the homepage if the user is not logged in
        window.location.href = "http://localhost:63342/EcommerceApp/static/index.html";
    }

});
