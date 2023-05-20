export const winLoadingHtml = `<!DOCTYPE html>
<html>
    <head>
        <title>Window with Blob</title>
    </head>
    <body>
    <style>
    body{
        min-height: 500px; 
    }
    #loader {
        width: 150px; height: 150px; margin-left: auto; margin-right: auto;
        margin-top:100px;
        display:block;
        border-radius:50%;
        border:3px solid transparent;
        border-top-color: rgb(54, 113, 172);
        -webkit-animation:spin 2s linear infinite;
        animation:spin 2s linear infinite;
        z-index:1001;
        }
        
        #loader:before {
        content:"";
        position:absolute;
        top:5px;
        left:5px;
        right:5px;
        bottom:5px;
        border-radius:50%;
        border:3px solid transparent;
        border-top-color:rgb(19, 100, 181);
        -webkit-animation:spin 3s linear infinite;
        animation:spin 3s linear infinite
        }
        
        #loader:after {
        content:"";
        position:absolute;
        top:15px;
        left:15px;
        right:15px;
        bottom:15px;
        border-radius:50%;
        border:3px solid transparent;
        border-top-color:rgb(109, 182, 255);
        -webkit-animation:spin 1.5s linear infinite;
        animation:spin 1.5s linear infinite
        }
        
        
        @-webkit-keyframes spin {
        0% {
            -webkit-transform:rotate(0deg);
            -ms-transform:rotate(0deg);
            transform:rotate(0deg)
        }
        100% {
            -webkit-transform:rotate(360deg);
            -ms-transform:rotate(360deg);
            transform:rotate(360deg)
        }
        }
        @keyframes spin {
        0% {
            -webkit-transform:rotate(0deg);
            -ms-transform:rotate(0deg);
            transform:rotate(0deg)
        }
        100% {
            -webkit-transform:rotate(360deg);
            -ms-transform:rotate(360deg);
            transform:rotate(360deg)
        }
        }
    </style>
        <div id="loader"></div>
    </body>
</html>`;