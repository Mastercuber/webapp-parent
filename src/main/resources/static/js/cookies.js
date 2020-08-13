/**
 * A cookie can be set and changed with this function.
 * You can set or change it for any path.
 *
 * @param key             Key of the cookie
 * @param value           Cookie Value
 * @param expiresInDays   Expiration of the cookie in days
 * @param path            Path the cookie will be set for. Default: "/"
 * @returns {boolean}     Cookie is set (true) or not (false)
 */
function setCookie(key, value, expiresInDays = 365, path = '/') {
  if (!key || !value) return false;

  const d = new Date();
  d.setTime(d.getTime() + (expiresInDays * 24 * 60 * 60 * 1000));
  const expires = `expires=${d.toUTCString()}`;
  document.cookie = `${key}=${value};${expires};path=${path}`;

  return true;
}

/**
 * A cookie can only be received if you on that path.
 * e.g. if you are on the path https://localhost:8080/ you can receive cookies which are set for the path "/".
 * On this path, "/", you can't receive cookies which are set for the path "/cart"
 *
 * @param key
 * @returns {string}
 */
function getCookie(key) {
  key += key + '=';
  const ca = document.cookie.split(';');

  for(let i = 0; i < ca.length; i++) {
    let c = ca[i];

    while (c.charAt(0) === ' ') {
      c = c.substring(1);
    }

    if (c.indexOf(key) == 0) {
      return c.substring(key.length, c.length);
    }
  }
  return '';
}

/**
 * Check if a cookie exists
 * @param key
 * @returns {boolean}
 */
function checkCookie(key) {
  const value = getCookie(key);
  if (value !== '') return true;
  return false;
}


/**
 * Delete a cookie
 * @param key
 * @param path
 * @returns {boolean}
 */
function deleteCookie(key, path) {
  let s = `${key}=;expires=Thu, 01 Jan 1970 00:00:00 UTC`;

  if (path) {
    s += `;path=${path}`
  }
  document.cookie = s;
  return true;
}

function addItemToCart(itemId) {
  if (!itemId || isNaN(itemId)) return false;

  const cartItemsStr = localStorage.getItem('cart-items');
  // shopping cart is empty
  if(!cartItemsStr) {
    localStorage.setItem('cart-items', JSON.stringify([itemId]))
  } else  {
    // add to existing cart items
    let cartItems = JSON.parse(cartItemsStr);
    cartItems.push(itemId);
    localStorage.setItem('cart-items', JSON.stringify(cartItems));
    alert('Das Spiel wurde dem Warenkorb hinzugefügt');
  }
}
