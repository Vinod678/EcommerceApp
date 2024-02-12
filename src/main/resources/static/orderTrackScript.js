document.addEventListener("DOMContentLoaded", function() {
    // Retrieve userId from localStorage
    const userId = localStorage.getItem('userId');

    // Fetch orders by userId
    fetch(`http://localhost:8080/orders/getOrdersByUserId?userId=${userId}`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(orders => {
        const orderListElement = document.getElementById('orderList');
        // Clear any existing content
        orderListElement.innerHTML = '';
        // Display each order in the order list
        orders.forEach(order => {
            const orderItem = document.createElement('div');
            orderItem.classList.add('orderItem');
            orderItem.innerHTML = `
                <h3>Order ID: ${order.orderId}</h3>
                <p>Product ID: ${order.productId}</p>
                <p>Quantity: ${order.quantity}</p>
                <p>Total Price: $${order.totalPrice.toFixed(2)}</p>
                <p>Status: ${order.status}</p>
                <p>Shipping Address: ${order.shippingAddress}</p>
                <p>Payment Method: ${order.paymentMethod}</p>
                <button class="trackButton" data-order-id="${order.orderId}">Track</button>
                <button class="cancelButton" data-order-id="${order.orderId}">Cancel</button>
                <hr>
            `;
            orderListElement.appendChild(orderItem);
        });

        // Add event listeners for track and cancel buttons
        const trackButtons = document.querySelectorAll('.trackButton');
        trackButtons.forEach(button => {
            button.addEventListener('click', () => {
                const orderId = button.dataset.orderId;
                // Implement track order functionality here
                console.log(`Track order with ID: ${orderId}`);
            });
        });

        const cancelButtons = document.querySelectorAll('.cancelButton');
        cancelButtons.forEach(button => {
            button.addEventListener('click', () => {
                const orderId = button.dataset.orderId;
                // Implement cancel order functionality here
                console.log(`Cancel order with ID: ${orderId}`);
            });
        });
    })
    .catch(error => {
        console.error('Error fetching orders:', error);
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
