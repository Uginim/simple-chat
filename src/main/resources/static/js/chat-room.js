
const stompClient = new StompJs.Client({
    brokerURL: `ws://${window.location.host}/api/ws`,
});

// 방번호 얻기
async function getRoomCode() {
    const xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('GET', `http://${window.location.host}/api/chat/room/create`, false);
    function sendAndWait(xmlHttpRequest) {
        return new Promise((resolve, reject) => {
            xmlHttpRequest.onreadystatechange = () => {
                if (xmlHttpRequest.readyState === 4 && xmlHttpRequest.status === 200) {
                    resolve(xmlHttpRequest.responseText);
                } else {
                    reject(new Error('error'));
                }
            };
            xmlHttpRequest.send();
        });
    }
    console.log('getRoomCode');
    const requestResponseText = await sendAndWait(xmlHttpRequest);

    console.log(requestResponseText);
    document.getElementById('roomCode').value = requestResponseText;
    return requestResponseText;
}

function connect() {
    console.log('Connecting to WS...');
    stompClient.activate();
}

stompClient.onConnect = function (options) {
    const roomCode = document.getElementById('roomCode').value;
    const result = stompClient.subscribe(`/chat/messages/${roomCode}`, function (message) {
        console.log(message);
        showMessage(JSON.parse(message.body).content);
    });
    console.log("subscribe result:", result);
};

stompClient.onWebSocketError = (error) => {
    console.log('Websocket Error', error);
};

stompClient.onStompError = (frame) => {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
};

const showMessage = (message) => {
    const messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode(message));
    document.getElementById('messages').appendChild(messageElement);
};

function sendMessage() {
    const chatRoomCode = document.getElementById('roomCode').value;
    const content = document.getElementById('message').value;
    console.log(content);
    stompClient.publish({
        // destination: `/app/send/${chatRoomCode}`, body: JSON.stringify({
        destination: `/app/send/message/${chatRoomCode}`, body: JSON.stringify({
            content: content,
            senderName: 'guest',
            messageType: 'CHAT'
        })
    });
}