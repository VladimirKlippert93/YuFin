import {Offer} from "../models/Offer";

export type OfferCardProps = {
    offer: Offer
}

export default function OfferCard(props: OfferCardProps){

    return(
        <div className={"offercard"}>
            {!props && <p>loading files...</p>}
            <div className={"offercard_border"}>
                <h2>{props.offer.title}</h2>
                <p>{props.offer.price}</p>
                <hr/>
                <p>{props.offer.address.city}</p>
            </div>
        </div>
    )
}