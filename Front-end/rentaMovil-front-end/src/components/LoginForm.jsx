import React ,{useState} from 'react';
function LoginForm({onSubmit}){   

        <section className="login">
            const [email, setEmail] = useState('');
            const [password, setPassword] = useState('');
            const [error, setError] = useState(null);
            const [login, setLogin] = useState(false);
            
            const handleSubmit = async (e) => {
                e.preventDefault();
                if (!email || !password)
                    return setError('')
            }
            <form onSubmit={onSubmit}>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                <button type="submit">Iniciar sesión</button>
            </form>
        </section>
   
   
    }
export default LoginForm;