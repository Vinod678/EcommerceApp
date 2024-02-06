    //Products Table available
    document.addEventListener("DOMContentLoaded", function () {
        const productList = document.getElementById('productList');
        const searchForm = document.querySelector('form');

        // Fetch all products initially
        fetchProducts();

        searchForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const searchInput = document.getElementById('searchProduct');
            const query = searchInput.value;

            // Fetch products based on the search query
            fetchProducts(query);
        });

        function fetchProducts(query = '') {
            // Clear the existing product list
            productList.innerHTML = '';

            // Fetch products from the backend
            const apiUrl = query ? `http://localhost:8080/products/search?query=${query}` : 'http://localhost:8080/products';

            fetch(apiUrl)
                .then(response => response.json())
                .then(products => {
                    // Update the UI with the product list
                    products.forEach(product => {
                        const productCard = document.createElement('div');
                        const productCardImg = document.createElement('div');
                        const img = document.createElement('img');
                        const h2 = document.createElement('h2');
                        const addButton = document.createElement('button');
                        const orderButton = document.createElement('button');

                        img.src = product.productImage;
                        img.width = '140';
                        img.height = '60';
                        img.align = 'center';

                        productCard.className = 'product-card';
                        productCard.appendChild(img);

                        productCard.innerHTML += `<p>${product.productID}</p><p>  ${product.productName}</p><p> Cost: ${product.productPrice}</p><p> Quantity: ${product.noOfProductsAvailable}</p>`;

                        addButton.innerText = 'Add To Cart';
                        orderButton.innerText = 'Buy';
                        addButton.addEventListener('click', () => {
                            addToCartItem(product.productID);
                        });

                        orderButton.addEventListener('click', function(){
                                                // Construct the URL with product details as query parameters
                                                const orderItemUrl = `http://localhost:63342/EcommerceApp/static/orderItem.html?productId=${product.productID}&productName=${encodeURIComponent(product.productName)}&productPrice=${product.productPrice}`;
                                                window.location.href = orderItemUrl;
                                            });

                        productCard.appendChild(addButton);
                        productCard.appendChild(orderButton);
                        productList.appendChild(productCard);
                    });
                })
                .catch(error => console.error('Error:', error));
        }

        function addToCartItem(productID) {
            // Make a POST request to add the item to the cart
            fetch('http://localhost:8080/cart/addToCart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    product: {
                        productID: productID,
                    },
                    quantity: 1, // You can set the quantity as needed
                }),
            })
                .then(response => response.json())
                .then(data => console.log('Item added to cart:', data))
                .catch(error => console.error('Error adding item to cart:', error));
        }
    });

//// Validate login form
//    const loginForm = document.getElementById('loginForm');
//    loginForm.addEventListener('submit', function (event) {
//        const emailInput = document.getElementById('loginEmail');
//        const passwordInput = document.getElementById('loginPassword');
//        if (!emailInput.value.trim()) {
//            event.preventDefault();
//            alert('Email is required.');
//            emailInput.focus();
//        } else if (!passwordInput.value.trim()) {
//            event.preventDefault();
//            alert('Password is required.');
//            passwordInput.focus();
//        }
//    });
//
//    // Validate registration form
//    const registerForm = document.getElementById('registerForm');
//    registerForm.addEventListener('submit', function (event) {
//        const emailInput = document.getElementById('registerEmail');
//        const passwordInput = document.getElementById('registerPassword');
//        const confirmPasswordInput = document.getElementById('confirmPassword');
//        if (!emailInput.value.trim()) {
//            event.preventDefault();
//            alert('Email is required.');
//            emailInput.focus();
//        } else if (!passwordInput.value.trim()) {
//            event.preventDefault();
//            alert('Password is required.');
//            passwordInput.focus();
//        } else if (!confirmPasswordInput.value.trim()) {
//            event.preventDefault();
//            alert('Confirm Password is required.');
//            confirmPasswordInput.focus();
//        }
//    });
//
//
// JavaScript for handling the popup windows
document.getElementById('login').addEventListener('click', function () {
    document.getElementById('loginPopup').style.display = 'block';
});

document.getElementById('registerFromLogin').addEventListener('click', function () {
    document.getElementById('registerPopup').style.display = 'block';
});

document.getElementById('loginFromRegister').addEventListener('click', function () {
    document.getElementById('registerPopup').style.display = 'none';
    document.getElementById('loginPopup').style.display = 'block';
});

function closePopup(popupId) {
    document.getElementById(popupId).style.display = 'none';
}





// Function to handle form submission for user registration
const registerForm = document.getElementById('registerForm');
registerForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission behavior
    const emailInput = document.getElementById('registerEmail').value;
    const passwordInput = document.getElementById('registerPassword').value;
    const confirmPasswordInput = document.getElementById('confirmPassword').value;

    // Validate the form inputs
    if (!emailInput.trim()) {
        alert('Email is required.');
        return;
    }
    if (!passwordInput.trim()) {
        alert('Password is required.');
        return;
    }
    if (!confirmPasswordInput.trim()) {
        alert('Confirm Password is required.');
        return;
    }
    if (passwordInput !== confirmPasswordInput) {
        alert('Passwords do not match.');
        return;
    }

    // Prepare the request body
    const requestBody = {
        userName: emailInput,
        password: passwordInput
    };

    // Send a POST request to the user registration API
    fetch('http://localhost:8080/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => {
        if (response.ok) {
            alert('User registered successfully.');
            document.getElementById('registerPopup').style.display = 'none'; // Hide the registration popup
        } else {
            alert('Failed to register user. Please try again.');
        }
    })
    .catch(error => {
        console.error('Error registering user:', error);
        alert('An error occurred while registering user. Please try again.');
    });
});

// Function to handle form submission for user login
const loginForm = document.getElementById('loginForm');
loginForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission behavior
    const emailInput = document.getElementById('loginEmail').value;
    const passwordInput = document.getElementById('loginPassword').value;

    // Validate the form inputs
    if (!emailInput.trim()) {
        alert('Email is required.');
        return;
    }
    if (!passwordInput.trim()) {
        alert('Password is required.');
        return;
    }

    // Prepare the request body
    const requestBody = {
        userName: emailInput,
        password: passwordInput
    };

    // Send a POST request to the user login API
    fetch('http://localhost:8080/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })

    .then(response => {
        if (response.ok) {
            alert('Login successful.');
            document.getElementById('loginPopup').style.display = 'none'; // Hide the login popup
            updateUIAfterLogin(emailInput.value.trim()); // Pass the email address
        } else {
            alert('Invalid username or password.');
        }
    })



    .catch(error => {
        console.error('Error logging in:', error);
        alert('An error occurred while logging in. Please try again.');
    });
});

function updateUIAfterLogin(email) {
    const loginButton = document.getElementById('login');
    loginButton.textContent = email; // Display the user's email instead of "User Login"

    const logoutButton = document.createElement('button');
    logoutButton.textContent = 'Logout';
    logoutButton.addEventListener('click', function() {
        // Handle logout functionality here, such as clearing session data or performing a logout API request
        // For now, let's just clear the login state from localStorage
        localStorage.removeItem('loggedIn'); // Remove the login state
        window.location.reload(); // Refresh the page
    });

    // Append the logout button next to the login button
    loginButton.insertAdjacentElement('afterend', logoutButton);

    // Set the login state in localStorage
    localStorage.setItem('loggedIn', true);
}



document.addEventListener("DOMContentLoaded", function () {
    const loggedIn = localStorage.getItem('loggedIn');

    if (loggedIn) {
        const email = 'user@example.com'; // Retrieve the user's email from localStorage or the server
        updateUIAfterLogin(email);
    } else {
        // Show the login button
        const loginButton = document.getElementById('login');
        loginButton.style.display = 'block'; // or any other appropriate way to display it
    }
});









