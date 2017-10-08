import React from 'react';
var Stomp = require('stompjs');
let client = Stomp.overWS('wss://localhost:3000/draw');

class Draw extends React.Component {

    constructor(props) {
        super(props);
        client.subscribe("/topic/progress", handleData);
        this.state = {
            count: 90
        };
    }

    handleData(data) {
        let result = JSON.parse(data);
        this.setState({count: this.state.count + result.movement});
    }

    render() {
        return (
            <div>
                Count: <strong>{this.state.count}</strong>
            </div>
        );
    }
}

export default Draw;