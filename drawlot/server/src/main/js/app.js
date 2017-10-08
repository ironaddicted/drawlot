const React = require('react');
const ReactDOM = require('react-dom');
const rest = require('rest');
/*const register = require('./registration');
const login = require('./login');
const draw = require('./draw');*/

import {  HashRouter, Switch, Route, Link } from 'react-router-dom';


import LoginForm from './login'
import RegisterForm from './registration'
import Draw from './draw'


class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {users: []};
    }

    componentDidMount() {
        rest({method: 'GET', path: '/api/v1/users'}).done(response => {
            this.setState({users: response.entity.json});
    });
    }

    render() {
        return (
            <div>
                <Header />
                <Main />
            </div>
    )
    }
}

const Main = () => (
    <main>
        <Switch>
            <Route exact path='/' component={Draw}/>
            <Route path='/register' component={LoginForm}/>
            <Route path='/login' component={RegisterForm}/>
        </Switch>
    </main>
);

const Header = () => (
    <header>
        <nav>
            <ul>
                <li><Link to='/'>Home</Link></li>
                <li><Link to='/register'>Register</Link></li>
                <li><Link to='/login'>Login</Link></li>
            </ul>
        </nav>
    </header>
);

ReactDOM.render((
    <HashRouter>
        <App />
    </HashRouter>),
    document.getElementById('react')
);