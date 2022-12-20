import {Offer} from "../models/Offer";
import {useNavigate} from "react-router-dom";
import * as React from 'react';
export type OfferCardProps = {
    offer: Offer
}

export default function OfferCard(props: OfferCardProps){

    const navigate = useNavigate()

    function handleDetailsClick(){
        navigate("/details/" + props.offer.id)
    }

    return(
        <div className={"offercard"}>
            {!props && <p>loading files...</p>}
            <div className={"offercard_border"} onClick={handleDetailsClick}>
                <h2>{props.offer.title}</h2>
                <p>{props.offer.price}</p>
                <hr/>
                <p>{props.offer.address.city}</p>
            </div>
        </div>
    )
}