document.addEventListener("DOMContentLoaded", function() {
    // Retrieve userId from localStorage
    const userId = localStorage.getItem('userId');

    // Function to check if the user is logged in
    function isUserLoggedIn() {
        // Retrieve the login status from localStorage
        const loggedIn = localStorage.getItem('loggedIn');
        // Check if the user is logged in based on the stored value
        return loggedIn === 'true';
    }

    // Function to fetch orders and populate the order list
    function fetchOrders() {
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
                        <button class="trackButton" data-order-id="${order.orderId}"${order.status === 'Canceled' ? ' disabled' : ''}>Track</button>
                        <button class="cancelButton" data-order-id="${order.orderId}"${order.status === 'Canceled' ? ' disabled' : ''}>Cancel Order</button>
                        <hr>
                    `;
                    orderListElement.appendChild(orderItem);
                });

                // Event listeners for track and cancel buttons
                const trackButtons = document.querySelectorAll('.trackButton');
                trackButtons.forEach(button => {
                    button.addEventListener('click', () => {
                        const orderId = button.dataset.orderId;
                        // Track order functionality
                        console.log(`Track order with ID: ${orderId}`);
                    });
                });

                const cancelButtons = document.querySelectorAll('.cancelButton');
cancelButtons.forEach(button => {
    button.addEventListener('click', () => {
        const orderId = button.dataset.orderId;
        // Ask for confirmation before canceling the order
        if (confirm("Are you sure you want to cancel the order?")) {
            // Calling cancel order API
            fetch(`http://localhost:8080/orders/cancelOrder/${orderId}`, {
                method: 'DELETE', // Use DELETE HTTP method to cancel the order
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to cancel order');
                }
                // Disable the cancel and track buttons after successful cancellation
                button.disabled = true;
                button.nextElementSibling.disabled = true; // Disable the corresponding track button
                console.log(`Order with ID ${orderId} canceled successfully`);
            })
            .catch(error => {
                console.error('Error canceling order:', error);
                // Handle errors
                alert('Failed to cancel order. Please try again later.');
            });
        }
    });
});

            })
            .catch(error => {
                console.error('Error fetching orders:', error);
            });
    }

    // Check if the user is logged in when the page loads
    if (!isUserLoggedIn()) {
        // Redirect to homepage if the user is not logged in
        window.location.href = "http://localhost:63342/EcommerceApp/static/index.html";
    } else {
        // Fetch orders and populate the order list
        fetchOrders();
    }
});
