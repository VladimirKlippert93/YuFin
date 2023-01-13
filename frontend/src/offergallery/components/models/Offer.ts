import {Address} from "./Address";

export type Offer = {
    id?: string,
    title: string,
    price: string,
    address: Address,
    description: string,
    author: string
}