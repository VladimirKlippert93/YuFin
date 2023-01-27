import {Offer} from "../models/Offer";
import OfferCard from "./OfferCard";
import {User} from "../models/User";
import "../../../styles/components/offers/OfferGallery.css"

type OfferGalleryProps = {
    offerList : Offer[],
    user: User
    deleteOffer: (id: string | undefined) => void
}

export default function OfferGallery(props: OfferGalleryProps){

    const offerComponents = props.offerList.map((offer)=>{
        return <OfferCard offer={offer} key={offer._id} user={props.user} deleteOffer={props.deleteOffer}/>
    })

    return(
        <div className="offer-gallery">
            {offerComponents}
        </div>
    )
}