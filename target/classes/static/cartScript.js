

// Function to fetch subtotal amount from API
function fetchSubTotal() {
    console.log('Fetching subtotal...');

    // Make AJAX request to fetch subtotal amount
    fetch('http://localhost:8080/cart/subTotalAmount')
        .then(response => {
            console.log('Response status:', response.status);
            return response.json();
        })
        .then(data => {
            console.log('Subtotal data received:', data);
            // Update Subtotal-value element with fetched subtotal amount
            document.getElementById('Subtotal-value').textContent = data.toFixed(2); // Round to 2 decimal places
        })
        .catch(error => console.error('Error fetching subtotal:', error));
}

// Call fetchSubTotal function when the page loads
window.onload = function() {
    console.log('Window loaded. Fetching subtotal...');
    fetchSubTotal();
};
