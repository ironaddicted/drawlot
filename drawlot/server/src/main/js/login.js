const React = require('react');
const REGISTER = 'register';

export default class LoginForm extends React.Component {

    render() {
        return (
            <div className="container">
                <div className="row main">
                    <div className="panel-heading">
                        <div className="panel-title text-center">
                            <h1 className="title">Company Name</h1>
                            <hr />
                        </div>
                    </div>
                    <div className="main-login main-center">
                        <form className="form-horizontal" method="post" action="#">

                            <div className="form-group">
                                <label htmlFor="name" className="cols-sm-2 control-label">Email</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-user fa" aria-hidden="true"></i></span>
                                        <input type="text" className="form-control" name="name" id="name"  placeholder="Your Email"/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label htmlFor="confirm" className="cols-sm-2 control-label">Password</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                        <input type="password" className="form-control" name="confirm" id="confirm"  placeholder="Your Password"/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group ">
                                <button type="button"  className="btn btn-primary btn-lg btn-block login-button">Login</button>
                            </div>
                            <div className="login-register">
                                <a onClick = {this.props.switchProp}>Register</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

module.exports = LoginForm;