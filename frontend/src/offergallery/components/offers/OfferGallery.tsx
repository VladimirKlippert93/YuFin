import {Offer} from "../models/Offer";
import OfferCard from "./OfferCard";

type OfferGalleryProps = {
    offerList : Offer[]
}

export default function OfferGallery(props: OfferGalleryProps){

    const offerComponents = props.offerList.map((offer)=>{
        return <OfferCard offer={offer} key={offer.id}/>
    })

    return(
        <div>
            {offerComponents}
        </div>
    )
}