import {Offer} from "../models/Offer";
import {useNavigate} from "react-router-dom";
import * as React from 'react';
import {User} from "../models/User";

export type OfferCardProps = {
    offer: Offer,
    user: User
    deleteOffer: (id: string | undefined) => void
}

export default function OfferCard(props: OfferCardProps){

    const navigate = useNavigate()

    function handleDetailsClick(){
        navigate("/offers/" + props.offer._id)
    }

    function deleteOffer(){
        props.deleteOffer(props.offer._id)
    }

    return(
        <div className={"offercard"}>
            {!props && <p>loading files...</p>}
            <div className={"offercard_border"} onClick={handleDetailsClick}>
                <h2>{props.offer.title}</h2>
                <p>{props.offer.price}</p>
                <p>{props.offer.address.city}</p>

            </div>
            {
                props.user.username && props.user.username !== "unknown User" && props.user.username === props.offer.author ?
                <button onClick={deleteOffer}>Delete</button>: ""
            }
            <hr/>
        </div>
    )
}