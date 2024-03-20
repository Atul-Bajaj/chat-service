'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();

//    login();
    const apiUrl = 'http://localhost:8080/user/validate';
    const data = {
      userName: document.querySelector('#name').value.trim(),
      password: document.querySelector('#pass').value.trim(),
    };

    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    };

    fetch(apiUrl, requestOptions)
      .then(response => {
      console.log(response);
        if (!response.ok) {
        username = null
        document.getElementById('alert').innerHTML = 'we have another problem!';
          throw new Error('Network response was not ok');
        }
        process();

//        username = document.querySelector('#name').value.trim();
      })
      .catch(error => {
        console.error

    ('Error:', error);
      });


    event.preventDefault();
}

function process() {
                usernamePage.classList.add('hidden');
                chatPage.classList.remove('hidden');

                var socket = new SockJS('/websocket');
                stompClient = Stomp.over(socket);

                stompClient.connect({}, onConnected, onError);
}

function login() {
    console.log('login');
    const apiUrl = 'http://localhost:8080/user/validate';
    const data = {
      userName: username,
      password: document.querySelector('#pass').value.trim(),
    };

    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    };

    fetch(apiUrl, requestOptions)
      .then(response => {
      console.log(response.status);
        if (!response.ok) {
        onError("response error");
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        outputElement.textContent = JSON.stringify(data, null, 2);
      })
      .catch(error => {
        console.error

    ('Error:', error);
      });
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.register",
        {},
        JSON.stringify({userName: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Unable to connect to WebSocket! Refresh the page and try again, or contact your administrator..';
    connectingElement.style.color = 'red';
}


function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            userName: username,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.userName + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.userName + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.userName[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.userName);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.userName);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)