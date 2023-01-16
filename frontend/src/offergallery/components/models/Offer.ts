import {Address} from "./Address";

export type Offer = {
    _id?: string,
    title: string,
    price: string,
    address: Address,
    description: string,
    author: string
}