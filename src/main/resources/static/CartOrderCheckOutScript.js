document.addEventListener("DOMContentLoaded", function () {
    const userId = localStorage.getItem('userId');
    const cartItemsContainer = document.getElementById('cartItems');
    const shippingInfoContainer = document.getElementById('shippingInfo');
    const paymentMethodSelect = document.getElementById('paymentMethodSelect');
    const totalPriceValue = document.getElementById('totalPriceValue');

    // Fetch cart items
    fetch(`http://localhost:8080/cart/getAllCartItems?userId=${userId}`)
        .then(response => response.json())
        .then(cartItems => {
            console.log('Cart items:', cartItems); // Log cartItems to inspect its structure

            // Ensure cartItems is an array before proceeding
            if (!Array.isArray(cartItems)) {
                throw new Error('Cart items data is not in the expected format');
            }

            // Display cart items
            cartItems.forEach(item => {
                const div = document.createElement('div');
                div.innerHTML = `
                    <h2>Product Name: ${item.product.productName}</h2>
                    <p>Product ID: ${item.product.productID}</p>
                    <p>Quantity: ${item.quantity}</p>
                    <p>Price: ${item.product.productPrice}</p>
                `;
                cartItemsContainer.appendChild(div);
            });
        })
        .catch(error => console.error('Error fetching or displaying cart items:', error));

// Fetch shipping information
const userEmail = localStorage.getItem('userEmail');
const apiUrl = `http://localhost:8080/user-profile/${userEmail}`;
fetch(apiUrl)
    .then(response => response.json())
    .then(userInfo => {
        // Populate shipping information fields
        document.getElementById('userEmailValue').textContent = userInfo.userEmail;
        document.getElementById('userNameValue').textContent = userInfo.userName;
        document.getElementById('userPhoneNumberValue').textContent = userInfo.userPhoneNumber;
        document.getElementById('userAddressValue').textContent = userInfo.userAddress;
    })
    .catch(error => console.error('Error fetching shipping information:', error));


    // Function to fetch total price amount from API
    function fetchTotalPrice() {
        console.log('Fetching total price...');
        const url = `http://localhost:8080/cart/subTotalAmount?userId=${userId}`;

        // Make AJAX request to fetch total price amount
        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch total price');
                }
                return response.json();
            })
            .then(data => {
                console.log('Total price data received:', data);
                // Update totalPriceValue element with fetched total price amount
                totalPriceValue.textContent = `$${data.toFixed(2)}`; // Round to 2 decimal places and prepend $
            })
            .catch(error => console.error('Error fetching total price:', error));
    }

    // Call fetchTotalPrice function when the page loads
    window.onload = function() {
        console.log('Window loaded. Fetching total price...');
        fetchTotalPrice();
    };

    // Add event listener for Place Order button
    document.getElementById('placeOrderButton').addEventListener('click', function () {
        // Fetch cart items again to ensure the latest data
        fetch(`http://localhost:8080/cart/getAllCartItems?userId=${userId}`)
            .then(response => response.json())
            .then(cartItems => {
                // Ensure cartItems is an array before proceeding
                if (!Array.isArray(cartItems)) {
                    throw new Error('Cart items data is not in the expected format');
                }

                // Prepare order data
                const orders = cartItems.map(item => ({
                    userId: userId,
                    productId: item.product.productID,
                    quantity: item.quantity,
                    totalPrice: parseFloat(item.product.productPrice.replace('$', '')),
                    orderDate: new Date().toISOString(),
                    status: "Pending",
                   shippingName: shippingInfoContainer.querySelector('#userNameValue').textContent.trim(),
                   shippingPhoneNumber: shippingInfoContainer.querySelector('#userPhoneNumberValue').textContent.trim(),
                   shippingAddress: shippingInfoContainer.querySelector('#userAddressValue').textContent.trim(),
                   paymentMethod: paymentMethodSelect.value
                }));

                // Send order data to backend for checkout
                fetch('http://localhost:8080/orders/createOrders', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(orders)
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Failed to place order');
                        }
                        alert('Order placed successfully!');
                        window.location.href = 'http://localhost:63342/EcommerceApp/static/orderConfirmation.html';
                    })
                    .catch(error => {
                        console.error('Error placing order:', error);
                        alert('Failed to place order. Please try again later.');
                    });
            })
            .catch(error => console.error('Error fetching cart items for order placement:', error));
    });


    console.log(shippingInfoContainer);
    console.log(shippingInfoContainer.querySelector('#userName'));
    console.log(shippingInfoContainer.querySelector('#userPhoneNumber'));
    console.log(shippingInfoContainer.querySelector('#userAddress'));

//  Event listener for Edit Order button
    document.getElementById('EditOrder').addEventListener('click', function () {
        window.location.href = 'http://localhost:63342/EcommerceApp/static/cart.html';
    });




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


