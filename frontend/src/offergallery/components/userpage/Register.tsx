import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";

type RegisterProps = {
    register: (user: string, password: string, email: string)=>void
}

const Register = (props: RegisterProps) => {

    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [email, setEmail] = useState<string>("")

    const navigation = useNavigate()

    function onChangeUser(event: ChangeEvent<HTMLInputElement>){
        setUsername(event.target.value)
    }

    function onChangeEmail(event: ChangeEvent<HTMLInputElement>){
        setEmail(event.target.value)
    }

    function onChangePassword(event: ChangeEvent<HTMLInputElement>){
        setPassword(event.target.value)
    }

    function onRegisterSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        props.register(username,password,email)
        setUsername("")
        setPassword("")
        setEmail("")
        navigation("/login")
    }

    return(
        <div>
            <div>
                <h4>Register</h4>
            </div>
            <div>
                <form onSubmit={onRegisterSubmit}>
                    <div>
                        {username.length >= 1 ?
                            <input onChange={onChangeUser}
                                   value={username}
                                   type="text"
                                   placeholder="Name"/> :
                            <input type="test"
                                    onChange={onChangeUser}
                                    placeholder="Name"
                                    value={username}/>
                        }
                        <label>Name</label>
                    </div>
                    <div>
                        {password.length <= 8 ?
                        <input onChange={onChangePassword}
                                value={password}
                                type="password"
                                placeholder="Password"/>:
                        <input onChange={onChangePassword}
                                value={password}
                                type="password"
                                placeholder={"Password"}/>
                        }
                        <label>Password</label>
                        <div>
                            <p>The Password needs at least 8 Characters.</p>
                        </div>
                        <div>
                            {email.includes('@') ?
                                <input onChange={onChangeEmail}
                                       value={email}
                                       type="email"
                                       placeholder="E-Mail"/> :
                                <input onChange={onChangeEmail}
                                       value={email}
                                       type="email"
                                       placeholder={"E-Mail"}/>
                            }
                            <label>E-Mail</label>
                        </div>
                    </div>
                    <div>
                        <button type={"submit"}>Register</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Register