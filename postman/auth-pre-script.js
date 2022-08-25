[В ответ на arBOT]
function parseJwt (token,part) {
   var base64Url = token.split('.')[part];
   var words = CryptoJS.enc.Base64.parse(base64Url);
   var jsonPayload = CryptoJS.enc.Utf8.stringify(words);
   return  JSON.parse(jsonPayload);
};

function login () {
   const getOTP = {
  method: 'GET',
  url: ${pm.environment.get('arnote-host')}/login?username=${pm.environment.get('user')}&password=${pm.environment.get('pass')},
  header: {
      'Content-Type': 'application/x-www-form-urlencoded'
  },
  
};

pm.sendRequest(getOTP, (mtzErr, res) => {
   var authHeader = res.headers.find(h => h.key == 'Authorization');
    console.log(authHeader);
   pm.request.headers.add({key: "Authorization", value: authHeader.value});
   postman.setEnvironmentVariable("artoken", authHeader.value);
})
};

if (!postman.getEnvironmentVariable("artoken")){
            login();
} else {
    
    
    var jwtInfo ={};
    var jwtData = postman.getEnvironmentVariable("artoken");
    pm.request.headers.add({key: "Authorization", value: jwtData});
    jwtInfo.size = jwtData.length;
    jwtInfo.payload = parseJwt(jwtData,1);
    jwtInfo.expires = ((jwtInfo.payload.exp-Date.now().valueOf()/1000)/60).toFixed(1);
    
    if (jwtInfo.expires < 60) {
            console.log("Токен протух! Получаем новый");
            login();
    }
   // console.log(jwtInfo);
}