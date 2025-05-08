import Header from '@/components/Header'
import { createRootRoute } from '@tanstack/react-router'
import { TanStackRouterDevtools } from '@tanstack/react-router-devtools'

export const Route = createRootRoute({
  component: () => (
    <>
      <Header />
      <div>
        Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ullam
        voluptatem omnis sed inventore ea ipsum cupiditate? Consequuntur,
        sapiente fugiat pariatur corporis amet reiciendis illo quas ipsam! Esse
        commodi quam voluptatum. Lorem ipsum dolor sit amet consectetur
        adipisicing elit. Quisquam aperiam obcaecati, error, corporis nobis
        corrupti explicabo expedita veniam sed voluptate itaque! Itaque
        doloremque obcaecati ab vitae harum maiores delectus magni!amet
        consectetur adipisicing elit. Ullam voluptatem omnis sed inventore ea
        ipsum cupiditate? Consequuntur, sapiente fugiat pariatur corporis amet
        reiciendis illo quas ipsam! Esse commodi quam voluptatum. Lorem ipsum
        dolor sit amet consectetur adipisicing elit. Quisquam aperiam obcaecati,
        error, corporis nobis corrupti explicabo expedita veniam sed voluptate
        itaque! Itaque doloremque obcaecati ab vitae harum maiores delectus
        magni!amet consectetur adipisicing elit. Ullam voluptatem omnis sed
        inventore ea ipsum cupiditate? Consequuntur, sapiente fugiat pariatur
        corporis amet reiciendis illo quas ipsam! Esse commodi quam voluptatum.
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam
        aperiam obcaecati, error, corporis nobis corrupti explicabo expedita
        veniam sed voluptate itaque! Itaque doloremque obcaecati ab vitae harum
        maiores delectus magni!amet consectetur adipisicing elit. Ullam
        voluptatem omnis sed inventore ea ipsum cupiditate? Consequuntur,
        sapiente fugiat pariatur corporis amet reiciendis illo quas ipsam! Esse
        commodi quam voluptatum. Lorem ipsum dolor sit amet consectetur
        adipisicing elit. Quisquam aperiam obcaecati, error, corporis nobis
        corrupti explicabo expedita veniam sed voluptate itaque! Itaque
        doloremque obcaecati ab vitae harum maiores delectus magni!amet
        consectetur adipisicing elit. Ullam voluptatem omnis sed inventore ea
        ipsum cupiditate? Consequuntur, sapiente fugiat pariatur corporis amet
        reiciendis illo quas ipsam! Esse commodi quam voluptatum. Lorem ipsum
        dolor sit amet consectetur adipisicing elit. Quisquam aperiam obcaecati,
        error, corporis nobis corrupti explicabo expedita veniam sed voluptate
        itaque! Itaque doloremque obcaecati ab vitae harum maiores delectus
        magni!
      </div>
      <TanStackRouterDevtools />
    </>
  ),
})
