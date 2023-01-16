import {Offer} from "./Offer";

export type User = {
    username: string,
    password: string,
    email: string,
    offerList: Offer[]
}