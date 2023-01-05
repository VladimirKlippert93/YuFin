import {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";

type LoginProps = {
    login: (name: string, password: string) => Promise<string>
}

const Login = (props: LoginProps) => {

    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const navigate = useNavigate()

    function onChangeName(event: ChangeEvent<HTMLInputElement>){
        setName(event.target.value)
    }

    function onChangePassword(event: ChangeEvent<HTMLInputElement>){
        setPassword(event.target.value)
    }

    function onLoginSubmit(event: FormEvent<HTMLFormElement>){
        event.preventDefault()
        props.login(name,password)
            .then(user=>{
                navigate("/")
            })
    }

    return(
        <div>
            <div>
                <h4>Login</h4>
            </div>
            <div>
                <form onSubmit={onLoginSubmit}>
                    <div>
                        <input onChange={onChangeName}
                               value={name}
                               type="text"
                               placeholder="Name"/>
                        <label>Name</label>
                    </div>
                    <div>
                        <input onChange={onChangePassword}
                               value={password}
                               type="password"
                               placeholder="Password"/>
                        <label>Password</label>
                    </div>
                    <div>
                        <button type={"submit"}>Login</button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Login