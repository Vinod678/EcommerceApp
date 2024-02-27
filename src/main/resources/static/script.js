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

//                        productCard.innerHTML += `<p>${product.productID}</p><p>  ${product.productName}</p><p> Cost: ${product.productPrice}</p><p> Quantity: ${product.noOfProductsAvailable}</p>`;

                        // Create and append <p> elements with specific IDs
                        const productIdParagraph = document.createElement('p');
                        productIdParagraph.id = 'productId';
                        productIdParagraph.textContent = product.productID;
                        productCard.appendChild(productIdParagraph);

                        const productNameParagraph = document.createElement('p');
                        productNameParagraph.id = 'productName';
                        productNameParagraph.textContent = product.productName;
                        productCard.appendChild(productNameParagraph);

                        const productDescriptionParagraph = document.createElement('p');
                        productDescriptionParagraph.id = 'productDescription';
                        productDescriptionParagraph.textContent = product.productDescription;
                        productCard.appendChild(productDescriptionParagraph);

                        const productPriceParagraph = document.createElement('p');
                        productPriceParagraph.id = 'productPrice';
                        productPriceParagraph.textContent = ` ${product.productPrice}`;
                        productCard.appendChild(productPriceParagraph);

                        const productQuantityParagraph = document.createElement('p');
                        productQuantityParagraph.id = 'productQuantity';
                        productQuantityParagraph.textContent = `Quantity: ${product.noOfProductsAvailable}`;
                        productCard.appendChild(productQuantityParagraph);

                        const loggedIn = localStorage.getItem('loggedIn');

                        if (loggedIn) {
                            const userId = localStorage.getItem('userId');
                            const productId = product.productID;
                            const productQuantity = product.noOfProductsAvailable;
                            console.log('productQuantity :', productQuantity);
                            console.log('productId :', productId);

                            // Define functions for adding and removing items from the cart
                            function addToCartHandler(userId, productId, addButton) {
                                addToCartItem(productId, /* other product details */);
                                addButton.innerText = 'Remove From Cart';
                                addButton.removeEventListener('click', addToCartHandler); // Remove the current event listener
                                addButton.addEventListener('click', () => removeFromCartHandler(userId, productId, addButton)); // Add a new event listener for removing from cart
                            }

                            function removeFromCartHandler(userId, productId, addButton) {
                                removeFromCart(userId, productId);
                                addButton.innerText = 'Add To Cart';
                                addButton.removeEventListener('click', removeFromCartHandler); // Remove the current event listener
                                addButton.addEventListener('click', () => addToCartHandler(userId, productId, addButton)); // Add a new event listener for adding to cart
                            }

                            // Check if the user ID is available
                           if (userId) {
                            // Check if the product is in the cart
                            fetch(`http://localhost:8080/cart/checkIfProductInCart/${userId}?productId=${productId}`)
                                .then(response => response.json())
                                .then(exists => {
                                    if (exists && productQuantity > 0) {

                                        addButton.innerText = 'Remove From Cart';
                                        addButton.addEventListener('click', () => removeFromCartHandler(userId, productId, addButton));
                                    } else if (productQuantity > 0) {
                                        addButton.innerText = 'Add To Cart';
                                        addButton.addEventListener('click', () => addToCartHandler(userId, productId, addButton));
                                    }
                                    else {
                                        // Product is out of stock, handle accordingly
                                        addButton.innerText = 'Out of Stock';
                                        addButton.disabled = true;
                                        orderButton.disable=true;
                                    }
                                })
                                .catch(error => console.error('Error checking product in cart:', error));
                                    } else {
                                        console.error('User ID not found in local storage');
                                    }
                        // Enable or disable the Buy button based on product availability
                        if (productQuantity > 0) {
                            orderButton.innerText = 'Buy';
                            orderButton.addEventListener('click', function () {
                                // Construct the URL with product details as query parameters
                                const orderItemUrl = `http://localhost:63342/EcommerceApp/static/orderItem.html?productId=${product.productID}`;
                                window.location.href = orderItemUrl;
                            });
                        } else {
                             // Hide the entire button if it's out of stock
                                orderButton.style.display = 'none';
                            }


                        } else {
                              addButton.innerText = 'Add To Cart';
                              addButton.addEventListener('click', () => {
                                  // Show login popup when the button is clicked
                                  document.getElementById('loginPopup').style.display = 'block';
                              });



                        orderButton.innerText = 'Buy';
                        orderButton.addEventListener('click', () => {
                        // Show login popup when the button is clicked
                        document.getElementById('loginPopup').style.display = 'block';
                                                    });
                        }

                        productCard.appendChild(addButton);
                        productCard.appendChild(orderButton);
                        productList.appendChild(productCard);
                    });
                })
                .catch(error => console.error('Error:', error));
        }

        function removeFromCart(userId, productId) {
            // Make a POST request to remove the item from the cart
            const url = `http://localhost:8080/cart/clearCartItemByProductId/${userId}?productId=${productId}`;

            fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    productId: productId
                }),
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Item removed from cart:', data);
                    // Refresh the product list after removing from cart
                    fetchProducts();
                })
                .catch(error => console.error('Error removing item from cart:', error));
        }





        function addToCartItem(productID,productName,productDescription,productPrice,noOfProductsAvailable,productImage) {
            // Retrieve the userId from localStorage
           const userId = localStorage.getItem('userId');
            const url = `http://localhost:8080/cart/addToCart?userId=${userId}`;

            // Make a POST request to add the item to the cart
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    product: {
                        productID: productID,
                        productName: productName,
                        productDescription: productDescription,
                        productPrice: productPrice,
                        noOfProductsAvailable: noOfProductsAvailable,
                        productImage: productImage
                    },
                    userId: userId, // Pass userId as a parameter
                    quantity: 1, // You can set the quantity as needed
                }),
            })
                .then(response => response.json())
                .then(data => console.log('Item added to cart:', data))
                .catch(error => console.error('Error adding item to cart:', error));
        }
    });

    //Shopping Cart will only visible if User Logins
      document.addEventListener("DOMContentLoaded", function() {
            const loggedIn = localStorage.getItem('loggedIn');
            const shoppingCartDiv = document.getElementById('shoppingCart');

            if (loggedIn) {
                shoppingCartDiv.style.display = 'block';
            }
        });




//    Making call to getUserIdByUserEmail to fetch the userID
    // Retrieve the userEmail from localStorage
    const userEmail = localStorage.getItem('userEmail');
    const loggedIn = localStorage.getItem('loggedIn');

    if(loggedIn){

    if (userEmail) {
      // Construct the URL for the API request
      const url = `http://localhost:8080/users/getUserIdByUserEmail?userEmail=${userEmail}`;

      // Make the API request
      fetch(url)
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          const userId = data; // Assuming data returned by the API is the userId
          localStorage.setItem('userId', userId);
          console.log('userId stored in localStorage:', userId);
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });
    } else {
      console.error('userEmail not found in localStorage');
    }
    }









// JavaScript for handling the Login -Register popup windows
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

// Function to handle form submission for USER REGISTRATION
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
        userEmail: emailInput,
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
            document.getElementById('emailError').textContent = 'User already exists. Please use a different email.';
//            alert('User Already Exists ,Use different Email');
        }
    })
    .catch(error => {
        console.error('Error registering user:', error);
        alert('An error occurred while registering user. Please try again.');
    });
});


// Function to handle form submission for USER LOGIN
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
        userEmail: emailInput,
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
//            alert('Login successful.');
            document.getElementById('loginPopup').style.display = 'none'; // Hide the login popup
            updateUIAfterLogin(emailInput.trim()); // Pass the email address without accessing .value
            window.location.reload(); // Refresh the page after successful login
        } else {
             document.getElementById('emailOrPasswordError').textContent = 'Invalid username or password';
//            alert('Invalid username or password.');
        }
    })
    .catch(error => {
        console.error('Error logging in:', error);
        alert('An error occurred while logging in. Please try again.');
    });
});


function updateUIAfterLogin(email) {
    // Set the login state and user email in localStorage first
    localStorage.setItem('loggedIn', true);
    localStorage.setItem('userEmail', email);

    const userEmailSpan = document.createElement('span');
    userEmailSpan.textContent = email;
    userEmailSpan.id = 'userEmail';
    userEmailSpan.style.cursor = 'pointer'; // Add pointer cursor for indicating clickability

    // Add click event listener to the user email span
    userEmailSpan.addEventListener('click', function() {
        // Show the popup for updating additional customer details
        document.getElementById('updateCustomerDetailsPopup').style.display = 'block';

        // Fetch user details and populate the form fields
        fetch(`http://localhost:8080/user-profile/${email}`)
        .then(response => response.json())
        .then(data => {
            // Populate form fields with user details
            document.getElementById('customerName').value = data.userName;
            document.getElementById('customerPhoneNumber').value = data.userPhoneNumber;
            document.getElementById('customerAddress').value = data.userAddress;
        })
        .catch(error => {
            console.error('Error fetching user details:', error);
            alert('Please update your details.');
        });
    });

    const logoutButton = document.createElement('button');
    logoutButton.textContent = 'Logout';
    logoutButton.id = 'logout';
    logoutButton.style.float = 'right';

    logoutButton.addEventListener('click', function() {
        // Handle logout functionality here, such as clearing session data or performing a logout API request
        // For now, let's just clear the login state from localStorage
        localStorage.removeItem('loggedIn'); // Remove the login state
        localStorage.removeItem('userEmail'); // Remove the user's email from localStorage
        localStorage.removeItem('userId'); // Remove the user's Id from localStorage
        window.location.reload(); // Refresh the page
    });

    const loginButton = document.getElementById('login');
    loginButton.parentNode.insertBefore(logoutButton, loginButton.nextSibling);
    loginButton.parentNode.insertBefore(userEmailSpan, logoutButton);
    loginButton.style.display = 'none'; // Hide the original login button
}




document.addEventListener("DOMContentLoaded", function () {
    const loggedIn = localStorage.getItem('loggedIn');

    if (loggedIn) {
        const email = localStorage.getItem('userEmail'); // Retrieve the user's email from localStorage
        updateUIAfterLogin(email);
    } else {
        // Show the login button
        const loginButton = document.getElementById('login');
        loginButton.style.display = 'block'; // or any other appropriate way to display it
    }
});



// Function to handle form submission for user profile details update - name , phoneNumber, address
const updateCustomerDetailsForm = document.getElementById('updateCustomerDetailsForm');
updateCustomerDetailsForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission behavior
    const customerNameInput = document.getElementById('customerName').value;
    const customerPhoneNumberInput = document.getElementById('customerPhoneNumber').value;
    const customerAddressInput = document.getElementById('customerAddress').value;

    // Validate the form inputs
    if (!customerNameInput.trim()) {
        alert('Name is required.');
        return;
    }
    if (!customerPhoneNumberInput.trim()) {
        alert('Phone Number is required.');
        return;
    }
    if (!customerAddressInput.trim()){
        alert('Address is required');
        return;
    }

    // Retrieve userEmail from localStorage
    const userEmail = localStorage.getItem('userEmail');

    // Prepare the request body
    const requestBody = {
        userName: customerNameInput,
        userPhoneNumber: customerPhoneNumberInput,
        userAddress: customerAddressInput
    };

    // Send a POST request to update user profile
    fetch(`http://localhost:8080/user-profile/update/${userEmail}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    })
    .then(response => {
        if (response.ok) {
            alert('User Details updated successfully.');
            document.getElementById('updateCustomerDetailsForm').reset(); // Reset form after successful update
            document.getElementById('updateCustomerDetailsPopup').style.display = 'none'; // Hide the popup
        } else {
            alert('Failed to update user details. Please try again.');
        }
    })
    .catch(error => {
        console.error('Error updating details:', error);
        alert('An error occurred while updating details. Please try again.');
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

document.addEventListener("DOMContentLoaded", function() {
    // Function to check if the user is logged in
    function isUserLoggedIn() {
        // Retrieve the login status from localStorage
        const loggedIn = localStorage.getItem('loggedIn');
        // Check if the user is logged in based on the stored value
        return loggedIn === 'true';
    }

    // Check if the user is logged in when the page loads
    if (!isUserLoggedIn()) {
        // Hide the "Track Orders" button if the user is not logged in
        const orderTrackButton = document.getElementById('orderTrack');
        orderTrackButton.style.display = 'none';
    }
});


//Order Track
document.addEventListener("DOMContentLoaded", function() {
        const orderTrackButton = document.getElementById('orderTrack');
        orderTrackButton.addEventListener('click', function() {
            window.location.href = 'http://localhost:63342/EcommerceApp/static/orderTrack.html';
        });
    });