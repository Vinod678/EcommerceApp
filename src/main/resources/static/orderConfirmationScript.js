document.addEventListener("DOMContentLoaded", function() {
    // Function to retrieve query parameters from the URL
    function getQueryParam(name) {
        const urlSearchParams = new URLSearchParams(window.location.search);
        return urlSearchParams.get(name);
    }

    // Retrieve order ID from query parameters
    const orderId = getQueryParam('orderId');

    // Display order confirmation message
    const confirmationMessage = document.getElementById('confirmationMessage');
    confirmationMessage.textContent = `Your order with ID ${orderId} has been confirmed. Thank you for shopping with us! You will be redirected to OrderTrack Page in few Seconds`;

// Redirect to order tracking page after a delay
    setTimeout(function() {
        window.location.href = 'http://localhost:63342/EcommerceApp/static/orderTrack.html';
    }, 5000); // 5000 milliseconds = 5 seconds





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
