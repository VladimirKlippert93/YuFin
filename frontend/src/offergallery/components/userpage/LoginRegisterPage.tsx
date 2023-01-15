import Register from "./Register";
import Login from "./Login";

type LoginProps = {
    login: (name: string, password: string) => Promise<string>
    register: (name: string, password: string, email:string) => void
}

const LoginRegisterPage = (props: LoginProps) => {

    return (
        <div>
            <div>
                <Register register={props.register}/>
            </div>
            <div>
                <Login login={props.login}/>
            </div>
        </div>
    )
}

export default LoginRegisterPage