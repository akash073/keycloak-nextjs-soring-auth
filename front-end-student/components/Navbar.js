import Link from "next/link";
import {signIn, signOut, useSession} from "next-auth/client";
import axios from "axios";
import getConfig from 'next/config'
import {useEffect} from "react";
const { serverRuntimeConfig, publicRuntimeConfig } = getConfig()

export const Navbar = () => {

    const [ session, loading ] = useSession();

    const logOut= async (e)=>{
        e.preventDefault();

        const token = session.accessToken
        const refresh_token = session.refreshToken;
        console.log('logOut',token);
        console.log('logOut',refresh_token);
        const logOutUrl = `http://localhost:8000/auth/realms/BANBEIS/protocol/openid-connect/logout`


        const params = new URLSearchParams()
        params.append('client_id', 'next-client')
        params.append('refresh_token', refresh_token)

        const config = {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }

        await axios.post(logOutUrl, params, config)
            .then((result) => {
                console.log(result);
                signOut({ callbackUrl: `/` })
            })
            .catch((err) => {
                console.log(err);
                signOut({ callbackUrl: `/` })
            })
    }


    return (
        <>
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <Link href="/">
                <a className="navbar-brand" href="#">Student SSO </a>
                </Link>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">

                            <Link href="/">
                                <a className="nav-link">Home</a>
                            </Link>
                        </li>
                        <li className="nav-item">
                            <Link href="/protected">
                                <a className="nav-link">Protected</a>
                            </Link>
                        </li>

                        {
                            session && <li className="nav-item">
                                <a className="nav-link" onClick={logOut}>Logout</a>
                            </li>
                        }

                        {
                            !session && <li className="nav-item">
                                <a className="nav-link"  onClick={() => signIn()}>Login</a>
                            </li>
                        }


                    </ul>

                </div>
            </nav>
        </>
    );
}