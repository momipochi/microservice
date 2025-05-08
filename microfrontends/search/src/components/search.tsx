import { useEffect, useRef, useState } from 'react'
import { Input } from './ui/input'
import { Button } from './ui/button'
const mockResults = [
  'Apple',
  'Banana',
  'Blueberry',
  'Mango',
  'Orange',
  'Strawberry',
]

export const Search = () => {
  const [query, setQuery] = useState('')
  const [open, setOpen] = useState(false)
  const wrapperRef = useRef(null)

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        wrapperRef.current &&
        !(wrapperRef.current as HTMLElement).contains(event.target as Node)
      ) {
        setOpen(false)
      }
    }

    document.addEventListener('mousedown', handleClickOutside)
  }, [])

  const filtered = mockResults.filter((item) =>
    item.toLowerCase().includes(query.toLowerCase()),
  )

  return (
    <div className="relative w-full max-w-md" ref={wrapperRef}>
      <div className="flex items-center gap-2 w-full">
        <Input
          type="search"
          placeholder="Search..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => setOpen(true)}
        />
        <Button>Search</Button>
      </div>

      {open && (
        <div className="absolute top-full left-0 mt-1 w-full bg-white border rounded-md shadow z-10">
          {filtered.map((x, i) => (
            <div
              className="px-3 py-2 hover:bg-gray-100 cursor-pointer"
              key={i}
              onClick={() => {
                console.log('Setting query: ', filtered[i])
                setQuery(filtered[i])
                setOpen(false)
              }}
            >
              {x}
            </div>
          ))}
        </div>
      )}
    </div>
  )
}
