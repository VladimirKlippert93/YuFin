import {Offer} from "../models/Offer";
import {useNavigate} from "react-router-dom";
import * as React from 'react';
import {User} from "../models/User";
import "../../../styles/components/offers/OfferCard.css"

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
                <h2 className="offercard_offer_title">{props.offer.title}</h2>
                <p className="offercard_offer_price">{props.offer.price}</p>
                <p className="offercard_offer_city">{props.offer.address.city}</p>

            </div>
            {
                props.user.username && props.user.username !== "unknown User" && props.user.username === props.offer.author ?
                <button onClick={deleteOffer}>Delete</button>: ""
            }
        </div>
    )
}